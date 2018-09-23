package com.baleit.footballmatchschedule.view

import com.baleit.footballmatchschedule.TestContextProvider
import com.baleit.footballmatchschedule.api.ApiCall
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.item.MatchItems
import com.baleit.footballmatchschedule.presenter.BallPresenter
import com.baleit.footballmatchschedule.respon.BallResponse
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class BallPresenterTest{
    @Mock
    private
    lateinit var view: BallView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepo

    private lateinit var presenter: BallPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = BallPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetTeamList() {
        val teams: MutableList<MatchItems> = mutableListOf()
        val response = BallResponse(teams)
        val league = "4328"

        `when`(gson.fromJson(apiRepository
                .doRequest(ApiCall.getNextEvent(league)),
                BallResponse::class.java
        )).thenReturn(response)

        presenter.getTeamListNext(league)

        verify(view).showLoading()
        verify(view).showMatch(teams)
        verify(view).hideLoading()
    }

}