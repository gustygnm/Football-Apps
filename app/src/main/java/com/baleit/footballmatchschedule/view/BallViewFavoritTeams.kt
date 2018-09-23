package com.baleit.footballmatchschedule.view

import com.baleit.footballmatchschedule.db.FavoriteTeams

interface BallViewFavoritTeams {
    fun showLoading()
    fun hideLoading()
    fun showNull()
    fun showMatch(data: List<FavoriteTeams>?)
}