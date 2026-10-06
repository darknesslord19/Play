package com.tavsiyefilmizle

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin
import android.content.Context

@CloudstreamPlugin
class TavsiyefilmizlePlugin: Plugin() {
    override fun load(context: Context) {
        registerMainAPI(Tavsiyefilmizle())
    }
}
