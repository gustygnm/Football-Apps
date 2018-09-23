package com.baleit.footballmatchschedule.presenter

import com.baleit.footballmatchschedule.api.ApiCall
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.idling.EspressoIdlingResource
import com.baleit.footballmatchschedule.respon.TeamsResponse
import com.baleit.footballmatchschedule.util.CoroutineContextProvider
import com.baleit.footballmatchschedule.view.TeamDetailView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepo,
                          private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        EspressoIdlingResource.increment();   // stops Espresso tests from going forward

        async(contextPool.main){
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(ApiCall.getTeamDetail(teamId)),
                        TeamsResponse::class.java
                )
            }

            view.showTeamDetail(data.await().teams)

            EspressoIdlingResource.decrement();   // Tells Espresso test to resume

            view.hideLoading()
        }
    }
}