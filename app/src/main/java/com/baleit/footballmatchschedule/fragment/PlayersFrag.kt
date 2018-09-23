package com.baleit.footballmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.baleit.footballmatchschedule.activity.PlayerDetailActivity
import com.baleit.footballmatchschedule.adapter.FavoriteTeamsAdapter
import com.baleit.footballmatchschedule.adapter.PlayersAdapter
import com.baleit.footballmatchschedule.db.FavoriteTeams
import com.baleit.footballmatchschedule.item.PlayersItems
import com.baleit.footballmatchschedule.presenter.PlayersPresenter
import com.baleit.footballmatchschedule.view.PlayersView


class PlayersFrag : Fragment(), PlayersView {

    private var player: MutableList<PlayersItems> = mutableListOf()
    private lateinit var adapter: PlayersAdapter
    private lateinit var id: String
    private lateinit var presenter: PlayersPresenter
    private lateinit var pbPlayer: ProgressBar
    private lateinit var rvPlayer: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = inflater?.inflate(R.layout.fragment_next, container, false)

        pbPlayer = views?.findViewById(R.id.pb_next) as ProgressBar
        rvPlayer = views?.findViewById(R.id.rv_next) as RecyclerView

        views?.let {

            adapter = PlayersAdapter(context, player) {
                ctx.startActivity<PlayerDetailActivity>("idPlayer" to "${it.idPlayer}","strPlayer" to "${it.strPlayer}")
            }

            val intent = activity!!.intent
            id = intent.getStringExtra("id")
            rvPlayer.layoutManager = LinearLayoutManager(ctx)
            rvPlayer.adapter = adapter
            presenter = PlayersPresenter(this, ApiRepo(), Gson())
            presenter.getPlayerList(id)
        }

        return views
    }

    override fun showLoading() {
        pbPlayer.visible()
    }

    override fun hideLoading() {
        pbPlayer.invisible()
    }

    override fun showPlayerList(data: List<PlayersItems>) {
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
    }

}
