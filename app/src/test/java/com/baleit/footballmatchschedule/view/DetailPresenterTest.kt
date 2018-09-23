package com.baleit.footballmatchschedule.view

import com.baleit.footballmatchschedule.TestContextProvider
import com.baleit.footballmatchschedule.api.ApiCall
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.item.DetailItems
import com.baleit.footballmatchschedule.item.TeamItems
import com.baleit.footballmatchschedule.presenter.DetailPresenter
import com.baleit.footballmatchschedule.respon.DetailResponse
import com.baleit.footballmatchschedule.respon.TeamResponse
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DetailPresenterTest {
    @Mock
    private
    lateinit var view: DetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepo

    private lateinit var presenter: DetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testLoadDetailMatch() {
        val home: MutableList<TeamItems> = mutableListOf()
        val away: MutableList<TeamItems> = mutableListOf()
        val detail: MutableList<DetailItems> = mutableListOf()
        val responseHome = TeamResponse(home)
        val responseAway = TeamResponse(away)
        val responseDetail = DetailResponse(detail)
        val homeId = "133624"
        val awayId = "133612"
        val detailId = "676518"

        `when`(gson.fromJson(apiRepository.doRequest(ApiCall.getTeams(homeId)), TeamResponse::class.java
        )).thenReturn(responseHome)

        `when`(gson.fromJson(apiRepository.doRequest(ApiCall.getTeams(awayId)), TeamResponse::class.java
        )).thenReturn(responseAway)

        `when`(gson.fromJson(apiRepository.doRequest(ApiCall.getDetailEvent(detailId)), DetailResponse::class.java
        )).thenReturn(responseDetail)

        presenter.loadDetailMatch(homeId, awayId, detailId)

        verify(view).showLoading()
        verify(view).showDetail(detail,home,away)
        verify(view).hideLoading()
    }
}