package com.baleit.footballmatchschedule.api

import com.baleit.footballmatchschedule.BuildConfig
import java.net.URL

class ApiRepo {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}