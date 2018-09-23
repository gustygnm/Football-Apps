package com.baleit.footballmatchschedule.pagerAdapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.baleit.footballmatchschedule.fragment.DeskripsiTeamFrag
import com.baleit.footballmatchschedule.fragment.PlayersFrag

class MyPagerAdapterDetailTeam(fm: FragmentManager?, internal var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> {
                return DeskripsiTeamFrag()
            }
            1 -> {
                return PlayersFrag()
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}