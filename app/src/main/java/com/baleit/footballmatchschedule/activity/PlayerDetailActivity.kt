package com.baleit.footballmatchschedule.activity

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.baleit.footballmatchschedule.R
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.baleit.footballmatchschedule.api.ApiRepo
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.jetbrains.anko.ctx
import com.baleit.footballmatchschedule.item.PlayerDetailItems
import com.baleit.footballmatchschedule.item.TeamsItems
import com.baleit.footballmatchschedule.presenter.PlayerDetailPresenter
import com.baleit.footballmatchschedule.view.PlayerDetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.toast

class PlayerDetailActivity() : AppCompatActivity(), PlayerDetailView {

    private lateinit var detailPresenter: PlayerDetailPresenter
    private lateinit var id: String


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val intent = intent
        id = intent.getStringExtra("idPlayer")
        supportActionBar!!.title = intent.getStringExtra("strPlayer")

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        detailPresenter = PlayerDetailPresenter(this, ApiRepo(), Gson())
        detailPresenter.getPlayerDetailList(id)
    }

    override fun showLoading() {
        pbDetails.visibility = View.VISIBLE
        mainLayout.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        pbDetails.visibility = View.INVISIBLE
        mainLayout.visibility = View.VISIBLE
    }

    override fun showPlayerDetailList(data: List<PlayerDetailItems>?) {
        val data = data?.get(0)
        Picasso.get().load(data?.strFanart1).into(image)
        txtBerat.text = data?.strWeight
        txtTinggi.text = data?.strHeight
        txtPosisi.text = data?.strPosition
        txtDeskripsi.text = data?.strDescriptionEN
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
