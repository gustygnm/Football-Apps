package com.baleit.footballmatchschedule.activity

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.adapter.BallAdapter
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.item.MatchItems
import com.baleit.footballmatchschedule.presenter.BallPresenter
import com.baleit.footballmatchschedule.presenter.MatchSearchPresenter
import com.baleit.footballmatchschedule.presenter.TeamsSearchPresenter
import com.baleit.footballmatchschedule.util.invisible
import com.baleit.footballmatchschedule.util.visible
import com.baleit.footballmatchschedule.view.BallView
import com.google.gson.Gson
import org.jetbrains.anko.appcompat.v7.expandedMenuView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class MatchSearchAvtivity : AppCompatActivity(), BallView {

    private val matchItems: MutableList<MatchItems>? = mutableListOf()
    private lateinit var searchPresenter: BallPresenter
    private lateinit var matchAdapter: BallAdapter
    private lateinit var pbNext: ProgressBar
    private lateinit var rvNext: RecyclerView

    override fun showLoading() {
        pbNext.visible()
        rvNext.invisible()
    }

    override fun hideLoading() {
        pbNext.invisible()
        rvNext.visible()
    }

    override fun showMatch(data: List<MatchItems>?) {
        data?.let {
            matchAdapter.refresh(it)
            if (data?.size == null) {
                toast(getString(R.string.upcoming_match))
            }
        }
    }

    override fun showNull() {
        toast(getString(R.string.upcoming_match))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_next)

        pbNext = findViewById(R.id.pb_next) as ProgressBar
        rvNext = findViewById(R.id.rv_next) as RecyclerView
        matchAdapter = matchItems?.let {
            BallAdapter(this, it) {
                startActivity<DetailActivity>(
                        "idHome" to it.homeTeamId, "idAway" to it.awayTeamId,
                        "goalHome" to it.scoreHome, "goalAway" to it.scoreAway,
                        "homeTeam" to it.teamHome, "awayTeam" to it.teamAway,
                        "dateMatch" to it.dateMatch.toString().dateFormat(), "matchId" to it.eventId
                )
            }
        }!!

        rvNext.layoutManager = LinearLayoutManager(this)
        rvNext.adapter = matchAdapter

        supportActionBar!!.title = "Pencarian"
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                startActivity<MainActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun String.dateFormat(format: String? = "EEEE, dd MMMM yyyy"): String {
        val date: Date = SimpleDateFormat("dd/MM/yy").parse(this)
        return SimpleDateFormat(format, Locale("in", "ID")).format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        menuInflater.inflate(R.menu.search, menu)
        val item = menu.findItem(R.id.action_search)
        if (item != null) {
            val searchView = SearchView((this).supportActionBar!!.themedContext)
            MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItemCompat.SHOW_AS_ACTION_IF_ROOM)
            MenuItemCompat.setActionView(item, searchView)
            searchView.queryHint = getString(R.string.telusuri)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchPresenter = BallPresenter(this@MatchSearchAvtivity, ApiRepo(), Gson())
                    searchPresenter.getMatchSearch(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
            searchView.clearFocus()
        }
        item.expandActionView()
        return true
    }
}
