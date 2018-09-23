package com.baleit.footballmatchschedule.pagerAdapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.baleit.footballmatchschedule.fragment.BallNextFrag
import com.baleit.footballmatchschedule.fragment.BallPrevFrag

class MyPagerAdapterMatch(fm: FragmentManager?, internal var mNumOfTabs: Int, private val matchId : String) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        val bundle = Bundle()
        bundle.putString("MATCH_ID", matchId)

        when (position) {
            0 -> {
                val fragment = BallPrevFrag()
                fragment.arguments = bundle
                return fragment
            }
            1 -> {
                val fragment = BallNextFrag()
                fragment.arguments = bundle
                return fragment
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}