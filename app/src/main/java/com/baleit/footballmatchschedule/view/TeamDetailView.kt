package com.baleit.footballmatchschedule.view

import com.baleit.footballmatchschedule.item.TeamsItems

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<TeamsItems>)
}