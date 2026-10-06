// use an integer for version numbers
version = 1

cloudstream {
    description = "Tavsiyefilmizle (otomatik uretildi)"
    authors = listOf("auto")

    /**
     * Status int as the following:
     * 0: Down
     * 1: Ok
     * 2: Slow
     * 3: Beta only
     */
    status = 1
    tvTypes = listOf("Movie")
    requiresResources = false
    language = "tr"

    iconUrl = "https://www.google.com/s2/favicons?domain=tavsiyefilmizle.net&sz=%size%"
}
