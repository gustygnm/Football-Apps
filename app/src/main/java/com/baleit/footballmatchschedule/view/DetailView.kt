package com.baleit.footballmatchschedule.view

import com.baleit.footballmatchschedule.item.DetailItems
import com.baleit.footballmatchschedule.item.TeamItems

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showBadge()
    fun showDetail(detail: List<DetailItems>?, home: List<TeamItems>?, away: List<TeamItems>?)
}