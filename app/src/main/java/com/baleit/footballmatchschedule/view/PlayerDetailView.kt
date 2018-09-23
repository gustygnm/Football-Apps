package com.baleit.footballmatchschedule.view

import com.baleit.footballmatchschedule.item.PlayerDetailItems
import com.baleit.footballmatchschedule.item.PlayersItems

interface PlayerDetailView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerDetailList(data: List<PlayerDetailItems>?)
}