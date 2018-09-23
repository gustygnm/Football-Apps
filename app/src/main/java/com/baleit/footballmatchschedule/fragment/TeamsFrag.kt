package com.baleit.footballmatchschedule.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.*
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.R.array.league
import com.baleit.footballmatchschedule.activity.TeamDetailActivity
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.item.TeamsItems
import com.baleit.footballmatchschedule.util.invisible
import com.baleit.footballmatchschedule.util.visible
import com.baleit.footballmatchschedule.adapter.TeamsAdapter
import com.baleit.footballmatchschedule.presenter.TeamsPresenter
import com.baleit.footballmatchschedule.view.TeamsView
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import android.widget.ArrayAdapter
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.*
import com.baleit.footballmatchschedule.activity.MainActivity
import android.view.MenuInflater
import com.baleit.footballmatchschedule.presenter.TeamsSearchPresenter
import kotlinx.android.synthetic.main.fragment_prev.*
import org.jetbrains.anko.support.v4.toast


class TeamsFrag : Fragment(), TeamsView {

    private var teams: MutableList<TeamsItems> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var searchPresenter: TeamsSearchPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var spinner: Spinner
    private lateinit var leagueName: String

    private var menuItem: Menu? = null
    private lateinit var pbTeams: ProgressBar
    private lateinit var rvTeams: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = inflater?.inflate(R.layout.fragment_teams, container, false)

        pbTeams = views?.findViewById(R.id.pb_teams) as ProgressBar
        rvTeams = views?.findViewById(R.id.rv_teams) as RecyclerView

        spinner = views?.findViewById(R.id.spinner_teams) as Spinner

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter<String>(ctx, R.layout.spinner_item, spinnerItems);
        spinner.adapter = spinnerAdapter

        views?.let {

            adapter = TeamsAdapter(context, teams) {
                ctx.startActivity<TeamDetailActivity>("id" to "${it.teamId}")
            }
            loadData()
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
               loadData()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        setHasOptionsMenu(true)
        return views
    }

    fun loadData() {
        rvTeams.layoutManager = LinearLayoutManager(ctx)
        rvTeams.adapter = adapter
        presenter = TeamsPresenter(this, ApiRepo(), Gson())
        leagueName = spinner.selectedItem.toString()
        presenter.getTeamList(leagueName)
    }

    override fun showLoading() {
        pbTeams.visible()
        rvTeams.invisible()
    }

    override fun hideLoading() {
        pbTeams.invisible()
        rvTeams.visible()
    }

    override fun showTeamList(data: List<TeamsItems>) {
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = SearchView((activity as MainActivity).supportActionBar?.themedContext)
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItemCompat.SHOW_AS_ACTION_IF_ROOM)
        MenuItemCompat.setActionView(item, searchView)
        searchView.queryHint = getString(R.string.telusuri)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchPresenter = TeamsSearchPresenter(this@TeamsFrag, ApiRepo(), Gson())
                searchPresenter.getTeamList(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
            }
        }
        )
    }
}

