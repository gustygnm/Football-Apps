package com.baleit.footballmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.item.TeamsItems
import com.baleit.footballmatchschedule.presenter.TeamDetailPresenter
import com.baleit.footballmatchschedule.util.invisible
import com.baleit.footballmatchschedule.util.visible
import com.baleit.footballmatchschedule.view.TeamDetailView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_deskripsi_team.*


class DeskripsiTeamFrag : Fragment(), TeamDetailView {
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: TeamsItems
    private lateinit var id: String
    lateinit var cardviewDesakripsi: CardView
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val views = inflater?.inflate(R.layout.fragment_deskripsi_team, container, false)

        cardviewDesakripsi=views?.findViewById(R.id.cardviewDesakripsi) as CardView
        progressBar=views?.findViewById(R.id.progressBar) as ProgressBar

        val intent = activity?.intent
        id = intent?.getStringExtra("id").toString()
        val request = ApiRepo()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)
        return views
    }

    override fun showLoading() {
        cardviewDesakripsi.invisible()
        progressBar.visible()
    }

    override fun hideLoading() {
        cardviewDesakripsi.visible()
        progressBar.invisible()
    }

    override fun showTeamDetail(data: List<TeamsItems>) {
        teamDescription.text = data[0].teamDescription
    }
}
