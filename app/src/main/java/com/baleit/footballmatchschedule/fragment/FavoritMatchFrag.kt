package com.baleit.footballmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.baleit.footballmatchschedule.activity.DetailActivity
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.adapter.FavoriteMatchAdapter
import com.baleit.footballmatchschedule.db.FavoriteMatch
import com.baleit.footballmatchschedule.db.database
import com.baleit.footballmatchschedule.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class FavoritMatchFrag : Fragment(), BallViewFavoritMatch {

    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var pbFavorit: ProgressBar
    private lateinit var rvFavorit: RecyclerView

    override fun showMatch(data: List<FavoriteMatch>?) {
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

            adapter = FavoriteMatchAdapter(context, favoriteMatches) {
                ctx.startActivity<DetailActivity>(
                        "idHome" to it.homeTeamId, "idAway" to it.awayTeamId,
                        "goalHome" to it.scoreHome, "goalAway" to it.scoreAway,
                        "homeTeam" to it.teamHome, "awayTeam" to it.teamAway,
                        "dateMatch" to it.dateMatch,
                        "matchId" to it.eventId
                )
            }

            rvFavorit.layoutManager = LinearLayoutManager(ctx)
            rvFavorit.adapter = adapter

            showFavorite()
        }

        return views
    }

    override fun onResume() {
        favoriteMatches.clear()
        showFavorite()
        super.onResume()
    }

    private fun showFavorite() {
        context?.database?.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favoriteMatches.addAll(favorite)
            adapter.notifyDataSetChanged()
            hideLoading()
        }
    }
}
