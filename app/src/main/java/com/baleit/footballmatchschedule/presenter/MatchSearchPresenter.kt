package com.baleit.footballmatchschedule.presenter

import com.google.gson.Gson
import com.baleit.footballmatchschedule.api.ApiCall
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.idling.EspressoIdlingResource
import com.baleit.footballmatchschedule.respon.BallResponse
import com.baleit.footballmatchschedule.respon.MatchSearchResponse
import com.baleit.footballmatchschedule.util.CoroutineContextProvider
import com.baleit.footballmatchschedule.view.BallView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchSearchPresenter(
        private val view: BallView,
        private val apiRepo: ApiRepo,
        private val gson: Gson,
        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getMatchSearch(eventName: String?) {
        view.showLoading()

        EspressoIdlingResource.increment();   // stops Espresso tests from going forward

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepo.doRequest(ApiCall.getSearchMatch(eventName)),
                        MatchSearchResponse::class.java
                )
            }
            view.showMatch(data.await().matchItems)

            EspressoIdlingResource.decrement();   // Tells Espresso test to resume

            view.hideLoading()
        }
    }
}