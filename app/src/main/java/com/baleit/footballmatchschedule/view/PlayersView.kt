package com.baleit.footballmatchschedule.view

import com.baleit.footballmatchschedule.item.PlayersItems

interface PlayersView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<PlayersItems>)
}