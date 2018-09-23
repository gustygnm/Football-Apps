package com.baleit.footballmatchschedule.view

import android.view.Menu
import android.view.MenuInflater
import com.baleit.footballmatchschedule.item.MatchItems

interface BallView {
    fun showLoading()
    fun hideLoading()
    fun showNull()
    fun showMatch(data: List<MatchItems>?)
}