package com.baleit.footballmatchschedule.fragment

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import java.text.SimpleDateFormat
import android.view.ViewGroup
import android.widget.ProgressBar
import com.baleit.footballmatchschedule.activity.DetailActivity
import com.google.gson.Gson
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.item.MatchItems
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.adapter.BallAdapter
import com.baleit.footballmatchschedule.presenter.BallPresenter
import com.baleit.footballmatchschedule.util.invisible
import com.baleit.footballmatchschedule.util.visible
import com.baleit.footballmatchschedule.view.BallView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import java.util.*

class BallNextFrag : Fragment(), BallView {

    private val matchItems: MutableList<MatchItems> = mutableListOf()
    private lateinit var matchPresenter: BallPresenter
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = inflater?.inflate(R.layout.fragment_next, container, false)

        pbNext = views?.findViewById(R.id.pb_next) as ProgressBar
        rvNext = views?.findViewById(R.id.rv_next) as RecyclerView
        views?.let {

            matchAdapter = BallAdapter(context, matchItems) {
                ctx.startActivity<DetailActivity>(
                        "idHome" to it.homeTeamId,
                        "idAway" to it.awayTeamId,
                        "goalHome" to it.scoreHome,
                        "goalAway" to it.scoreAway,
                        "homeTeam" to it.teamHome,
                        "awayTeam" to it.teamAway,
                        "dateMatch" to it.dateMatch.toString().dateFormat(),
                        "matchId" to it.eventId
                )
            }

            rvNext.layoutManager = LinearLayoutManager(ctx)
            rvNext.adapter = matchAdapter
            matchPresenter = BallPresenter(this, ApiRepo(), Gson())

            var idLeague: String = arguments?.getString("MATCH_ID","4328").toString()
            matchPresenter.getTeamListNext(idLeague)

        }
        return views
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun String.dateFormat(format: String? = "EEEE, dd MMMM yyyy"): String {
        val date: Date = SimpleDateFormat("dd/MM/yy").parse(this)
        return SimpleDateFormat(format, Locale("in", "ID")).format(date)
    }
}