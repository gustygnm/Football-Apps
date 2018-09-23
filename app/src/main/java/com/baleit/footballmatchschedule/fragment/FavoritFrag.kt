package com.baleit.footballmatchschedule.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.pagerAdapter.MyPagerAdapterFavorit

class FavoritFrag : Fragment() {

    private var tabLayout: TabLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val views = inflater?.inflate(R.layout.fragment_favorit, container, false)


        tabLayout = views.findViewById(R.id.tablayout_favorit) as TabLayout
        val viewPager = views.findViewById(R.id.viewpager_favorit) as ViewPager
        val adapter = MyPagerAdapterFavorit(fragmentManager, tabLayout!!.getTabCount())
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        return views
    }
}
