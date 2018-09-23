package com.baleit.footballmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.activity.TeamDetailActivity
import com.baleit.footballmatchschedule.adapter.FavoriteTeamsAdapter
import com.baleit.footballmatchschedule.db.FavoriteTeams
import com.baleit.footballmatchschedule.db.database
import com.baleit.footballmatchschedule.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class FavoritTeamsFrag : Fragment(), BallViewFavoritTeams {

    private var favoriteTeams: MutableList<FavoriteTeams> = mutableListOf()
    private lateinit var adapter: FavoriteTeamsAdapter

    private lateinit var pbFavorit: ProgressBar
    private lateinit var rvFavorit: RecyclerView

    override fun showMatch(data: List<FavoriteTeams>?) {
        data?.let {
            adapter.refresh(it)
        }
    }

    override fun hideLoading() {
        pbFavorit.visibility = View.INVISIBLE
    }

    override fun showNull() {
        toast(R.string.previous_match)
    }

    override fun showLoading() {
        pbFavorit.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val views = inflater?.inflate(R.layout.fragment_favorit_match, container, false)
        pbFavorit = views?.findViewById(R.id.pb_favorit) as ProgressBar
        rvFavorit = views?.findViewById(R.id.rv_favorit) as RecyclerView

        views?.let {

            adapter = FavoriteTeamsAdapter(context, favoriteTeams) {
                ctx.startActivity<TeamDetailActivity>("id" to "${it.teamId}")
            }
            rvFavorit.layoutManager = LinearLayoutManager(ctx)
            rvFavorit.adapter = adapter
        }
        return views
    }

    override fun onResume() {
        favoriteTeams.clear()
        showFavorite()
        super.onResume()
    }

    private fun showFavorite() {
        context?.database?.use {
            val result = select(FavoriteTeams.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteTeams>())
            favoriteTeams.addAll(favorite)
            adapter.notifyDataSetChanged()
            hideLoading()
        }
    }
}
