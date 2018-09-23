package com.baleit.footballmatchschedule.presenter

import com.baleit.footballmatchschedule.api.ApiCall
import com.google.gson.Gson
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.idling.EspressoIdlingResource
import com.baleit.footballmatchschedule.respon.DetailResponse
import com.baleit.footballmatchschedule.respon.TeamResponse
import com.baleit.footballmatchschedule.util.CoroutineContextProvider
import com.baleit.footballmatchschedule.view.DetailView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPresenter(
        private val view: DetailView,
        private val apiRepo: ApiRepo,
        private val gson: Gson,
        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun loadDetailMatch(homeId: String?, awayId: String?, matchId: String?) {
        view.showLoading()

        EspressoIdlingResource.increment();   // stops Espresso tests from going forward

        async(context.main) {
            val home = bg {
                gson.fromJson(apiRepo.doRequest(ApiCall.getTeams(homeId)),
                        TeamResponse::class.java
                )
            }
            val away = bg {
                gson.fromJson(apiRepo.doRequest(ApiCall.getTeams(awayId)),
                        TeamResponse::class.java
                )
            }
            val detail = bg {
                gson.fromJson(apiRepo.doRequest(ApiCall.getDetailEvent(matchId)),
                        DetailResponse::class.java
                )
            }
            view.showDetail(detail.await().detailItems, home.await().teamsItems, away.await().teamsItems)

            EspressoIdlingResource.decrement();   // Tells Espresso test to resume

            view.hideLoading()
        }
    }
}