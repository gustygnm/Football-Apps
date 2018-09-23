package com.baleit.footballmatchschedule.view

import com.baleit.footballmatchschedule.db.FavoriteMatch

interface BallViewFavoritMatch {
    fun showLoading()
    fun hideLoading()
    fun showNull()
    fun showMatch(data: List<FavoriteMatch>?)
}