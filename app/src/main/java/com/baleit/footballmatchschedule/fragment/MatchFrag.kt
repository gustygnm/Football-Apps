package com.baleit.footballmatchschedule.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.activity.DetailActivity
import com.baleit.footballmatchschedule.activity.MainActivity
import com.baleit.footballmatchschedule.activity.MatchSearchAvtivity
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.pagerAdapter.MyPagerAdapterMatch
import com.baleit.footballmatchschedule.presenter.MatchSearchPresenter
import com.baleit.footballmatchschedule.presenter.TeamsSearchPresenter
import com.google.gson.Gson
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class MatchFrag : Fragment() {

    private var tabLayout: TabLayout? = null
    private lateinit var spinner: Spinner
    lateinit var idLeague: String
    private lateinit var leagueName: String
    private lateinit var searchPresenter: MatchSearchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val views = inflater?.inflate(R.layout.fragment_match, container, false)

        spinner = views?.findViewById(R.id.spinner_teams) as Spinner
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter<String>(ctx, R.layout.spinner_item, spinnerItems);
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                loadData(views)
            }
        }
        loadData(views)
        setHasOptionsMenu(true)
        return views
    }

    fun loadData(views: View) {
        leagueName = spinner.selectedItem.toString()
        getIdLeague(leagueName)
        tabLayout = views.findViewById(R.id.tablayout_match) as TabLayout
        val viewPager = views.findViewById(R.id.viewpager_match) as ViewPager
        val adapter = tabLayout?.getTabCount()?.let { MyPagerAdapterMatch(fragmentManager, it, idLeague) }
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout?.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
        })
    }

    fun getIdLeague(leagueName: String) {
        if (leagueName.equals(getString(R.string.english_premier_league))) {
            idLeague = "4328"
        } else if (leagueName.equals(getString(R.string.english_league_championship))) {
            idLeague = "4329"
        } else if (leagueName.equals(getString(R.string.german_bundesliga))) {
            idLeague = "4331"
        } else if (leagueName.equals(getString(R.string.italian_serie_a))) {
            idLeague = "4332"
        } else if (leagueName.equals(getString(R.string.french_ligue_1))) {
            idLeague = "4334"
        } else if (leagueName.equals(getString(R.string.spanish_la_liga))) {
            idLeague = "4335"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                startActivity<MatchSearchAvtivity>()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
