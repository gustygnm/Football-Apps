package com.baleit.footballmatchschedule.fragment

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
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
import java.text.SimpleDateFormat
import java.util.*

class BallPrevFrag : Fragment(), BallView {

    private val matchItems: MutableList<MatchItems> = mutableListOf()
    private lateinit var matchPresenter: BallPresenter
    private lateinit var matchAdapter: BallAdapter
    private lateinit var leagueName: String
    private lateinit var pbPast: ProgressBar
    private lateinit var rvPast: RecyclerView



    override fun showMatch(data: List<MatchItems>?) {
        data?.let {
            matchAdapter.refresh(it)
        }
    }

    override fun hideLoading() {
        pbPast.invisible()
        rvPast.visible()
    }


    override fun showNull() {
        toast(getString(R.string.previous_match))
    }

    override fun showLoading() {
        pbPast.visible()
        rvPast.invisible()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = inflater?.inflate(R.layout.fragment_prev, container, false)

        pbPast = views?.findViewById(R.id.pb_past) as ProgressBar
        rvPast = views?.findViewById(R.id.rv_past) as RecyclerView
        views?.let {

            matchAdapter = BallAdapter(context, matchItems) {
                var dateMatch:String
                if (it.dateMatch.toString().equals("null")){
                    dateMatch= "No Date"
                } else{
                    dateMatch= it.dateMatch.toString().dateFormat()
                }
                ctx.startActivity<DetailActivity>(
                        "idHome" to it.homeTeamId, "idAway" to it.awayTeamId,
                        "goalHome" to it.scoreHome, "goalAway" to it.scoreAway,
                        "homeTeam" to it.teamHome, "awayTeam" to it.teamAway,
                        "dateMatch" to dateMatch, "matchId" to it.eventId
                )
            }

            rvPast.layoutManager = LinearLayoutManager(ctx)
            rvPast.adapter = matchAdapter
            matchPresenter = BallPresenter(this, ApiRepo(), Gson())

            var idLeague: String = arguments?.getString("MATCH_ID","4328").toString()
            matchPresenter.getTeamListPrev(idLeague)
        }
        return views
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun String.dateFormat(format: String? = "EEEE, dd MMMM yyyy"): String {
        val date: Date = SimpleDateFormat("dd/MM/yy").parse(this)
        return SimpleDateFormat(format, Locale("in", "ID")).format(date)
    }
}