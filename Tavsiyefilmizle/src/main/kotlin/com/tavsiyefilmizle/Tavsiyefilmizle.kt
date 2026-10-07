package com.tavsiyefilmizle

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element
import java.net.URLEncoder

class Tavsiyefilmizle : MainAPI() {
    override var mainUrl = "https://tavsiyefilmizle.org"
    override var name = "Tavsiye Film izle"
    override val supportedTypes = setOf(TvType.Movie)
    override var lang = "tr"
    override val hasMainPage = true

    override val mainPage = mainPageOf(
        "$mainUrl/page/" to "Son Eklenenler",
        "$mainUrl/category/en-iyi-filmler/page/" to "En İyi Filmler"
    )

    private fun Element.toResult(): SearchResponse? {
        val a = selectFirst("a[href]") ?: return null
        val href = fixUrlNull(a.attr("href")) ?: return null
        val title = (selectFirst("h2, .title")?.text() ?: a.attr("title").ifBlank { a.text() }).trim()
        if (title.isBlank()) return null
        val img = selectFirst("img")
        val poster = fixUrlNull(
            img?.attr("data-src")?.ifBlank { null }
                ?: img?.attr("data-lazy-src")?.ifBlank { null }
                ?: img?.attr("src")
        )
        return newMovieSearchResponse(title, href, TvType.Movie) { this.posterUrl = poster }
    }

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val items = try {
            app.get("${request.data}$page").document
                .select("article, .film-box, .post-item")
                .mapNotNull { it.toResult() }
                .distinctBy { it.url }
        } catch (e: Exception) {
            emptyList()
        }
        return newHomePageResponse(request.name, items, hasNext = items.isNotEmpty())
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val q = URLEncoder.encode(query, "UTF-8")
        return try {
            app.get("$mainUrl/?s=$q").document
                .select("article, .film-box, .post-item")
                .mapNotNull { it.toResult() }
                .distinctBy { it.url }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun load(url: String): LoadResponse? {
        val document = app.get(url).document
        val title = document.selectFirst("h1, .entry-title")?.text()?.trim()
            ?.takeIf { it.isNotBlank() } ?: return null
        val poster = fixUrlNull(
            document.selectFirst("meta[property=og:image]")?.attr("content")
                ?: document.selectFirst(".poster img, .entry-content img")?.attr("src")
        )
        val plot = document.selectFirst(".description, .entry-content p")?.text()
            ?: document.selectFirst("meta[property=og:description]")?.attr("content")

        return newMovieLoadResponse(title, url, TvType.Movie, url) {
            this.posterUrl = poster
            this.plot = plot
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val document = app.get(data).document
        var found = false
        for (iframe in document.select("iframe")) {
            val src = listOf("data-litespeed-src", "data-src", "data-lazy-src", "src")
                .map { iframe.attr(it) }
                .firstOrNull { it.startsWith("http") || it.startsWith("//") }
                ?: continue
            if (src.contains("youtube", ignoreCase = true)) continue
            if (loadExtractor(fixUrl(src), data, subtitleCallback, callback)) found = true
        }
        return found
    }
}
