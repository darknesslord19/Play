package com.tavsiyefilmizle

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.Jsoup

class Tavsiyefilmizle : MainUrlPlugin() {
    override var mainUrl = "https://tavsiyefilmizle.org"
    override var name = "Tavsiye Film izle"
    override val supportedTypes = setOf(TvType.Movie)
    override var lang = "tr"
    override val hasMainPage = true

    override val mainPage = mainPageOf(
        "$mainUrl/page/" to "Son Eklenenler",
        "$mainUrl/category/en-iyi-filmler/page/" to "En İyi Filmler"
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val document = app.get("${request.data}$page").document
        val home = document.select("article, .film-box, .post-item").mapNotNull {
            val title = it.selectFirst("h2, .title, a")?.text() ?: return@mapNotNull null
            val href = it.selectFirst("a")?.attr("href") ?: return@mapNotNull null
            val posterUrl = it.selectFirst("img")?.attr("src")

            newMovieSearchResponse(title, href, TvType.Movie) {
                this.posterUrl = posterUrl
            }
        }
        return newHomePageResponse(request.name, home)
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val document = app.get("$mainUrl/?s=$query").document
        return document.select("article, .post-item").mapNotNull {
            val title = it.selectFirst("h2, a")?.text() ?: return@mapNotNull null
            val href = it.selectFirst("a")?.attr("href") ?: return@mapNotNull null
            val posterUrl = it.selectFirst("img")?.attr("src")

            newMovieSearchResponse(title, href, TvType.Movie) {
                this.posterUrl = posterUrl
            }
        }
    }

    override suspend fun load(url: String): LoadResponse {
        val document = app.get(url).document
        val title = document.selectFirst("h1, .entry-title")?.text() ?: "Bilinmeyen Film"
        val poster = document.selectFirst(".poster img, .entry-content img")?.attr("src")
        val plot = document.selectFirst(".description, .entry-content p")?.text()

        return newMovieLoadResponse(title, url, TvType.Movie, url) {
            this.posterUrl = poster
            this.plot = plot
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCdn: Boolean,
        handler: PlaylistUtils,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val document = app.get(data).document
        val iframes = document.select("iframe")

        for (iframe in iframes) {
            val src = iframe.attr("src")
            if (src.isNotEmpty()) {
                loadExtractor(src, data, callback)
            }
        }
        return true
    }
}
