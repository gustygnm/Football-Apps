package com.baleit.footballmatchschedule.presenter

import com.baleit.footballmatchschedule.api.ApiCall
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.idling.EspressoIdlingResource
import com.baleit.footballmatchschedule.respon.PlayerDetailResponse
import com.baleit.footballmatchschedule.respon.PlayersResponse
import com.baleit.footballmatchschedule.respon.TeamsResponse
import com.baleit.footballmatchschedule.util.CoroutineContextProvider
import com.baleit.footballmatchschedule.view.PlayerDetailView
import com.baleit.footballmatchschedule.view.PlayersView
import com.baleit.footballmatchschedule.view.TeamsView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerDetailPresenter(private val view: PlayerDetailView,
                            private val apiRepository: ApiRepo,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerDetailList(playerId: String?) {
        view.showLoading()

        EspressoIdlingResource.increment();   // stops Espresso tests from going forward

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(ApiCall.getDetailPlayer(playerId)),
                        PlayerDetailResponse::class.java
                )
            }
            view.showPlayerDetailList(data.await().playerDetail)

            EspressoIdlingResource.decrement();   // Tells Espresso test to resume

            view.hideLoading()
        }
    }
}