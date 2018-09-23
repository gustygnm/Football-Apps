package com.baleit.footballmatchschedule.view

import com.baleit.footballmatchschedule.item.TeamsItems

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<TeamsItems>)
}