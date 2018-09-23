package com.baleit.footballmatchschedule.activity

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.baleit.footballmatchschedule.R
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.item.DetailItems
import com.baleit.footballmatchschedule.item.TeamItems
import com.baleit.footballmatchschedule.R.drawable.ic_add_to_favorites
import com.baleit.footballmatchschedule.R.drawable.ic_added_to_favorites
import com.baleit.footballmatchschedule.presenter.DetailPresenter
import com.baleit.footballmatchschedule.view.DetailView
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.ctx
import com.baleit.footballmatchschedule.R.id.*
import com.baleit.footballmatchschedule.R.menu.detail_menu
import com.baleit.footballmatchschedule.db.FavoriteMatch
import com.baleit.footballmatchschedule.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailActivity() : AppCompatActivity(), DetailView {

    private val detailItems: MutableList<DetailItems> = mutableListOf()

    private lateinit var detailPresenter: DetailPresenter

    private lateinit var homeId: String
    private lateinit var awayId: String
    private lateinit var matchId: String
    private var goalHome: String? = null
    private var goalAway: String? = null

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent
        id = intent.getStringExtra("matchId")
        ctx.let {
            val i = intent

            detailPresenter = DetailPresenter(this, ApiRepo(), Gson())
            txtDate.text = i.getStringExtra("dateMatch")
            homeTeam.text = i.getStringExtra("homeTeam")
            homeTeamSide.text = i.getStringExtra("awayTeam")

            goalHome = i.getStringExtra("goalHome")
            goalAway = i.getStringExtra("goalAway")
            homeScore.text = goalHome + " : " + goalAway

            if (homeScore.text.equals("null : null")) {
                homeScore.text = " vs "
            }
            homeId = i.getStringExtra("idHome")
            awayId = i.getStringExtra("idAway")
            matchId = i.getStringExtra("matchId")
            detailPresenter.loadDetailMatch(homeId, awayId, matchId)
        }

        favoriteState()
        supportActionBar?.title = "" + homeTeam.text + " vs " + homeTeamSide.text

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    override fun showLoading() {
        pbDetails.visibility = View.VISIBLE
        mainLayout.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        pbDetails.visibility = View.INVISIBLE
        mainLayout.visibility = View.VISIBLE
    }

    override fun showDetail(detail: List<DetailItems>?, home: List<TeamItems>?, away: List<TeamItems>?) {
        try {
            let {
                val data = detail?.get(0)
                val homeData = home?.get(0)
                val awayData = away?.get(0)

                //Replaces TeamsItems Home from Detail Results
                val goalHome: String? = data?.homeGoals
                goalHome?.replace(";", "\n")
                val redHome: String? = data?.homeRedCard
                redHome?.replace(";", "\n")
                val yellowHome: String? = data?.homeYellowCard
                yellowHome?.replace(";", "\n")
                val gkHome: String? = data?.homeGoalK
                gkHome?.replace(";", "\n")
                val defHome: String? = data?.homeDefense
                defHome?.replace(";", "\n")
                val midHome: String? = data?.homeMidfield
                midHome?.replace(";", "\n")
                val forHome: String? = data?.homeForward
                forHome?.replace(";", "\n")
                val subHome: String? = data?.homeSubstitutes
                subHome?.replace(";", "\n")

                //Replaces TeamsItems Away from Detail Results
                val goalAway: String? = data?.awayGoals
                goalAway?.replace(";", "\n")
                val redAway: String? = data?.awayRedCard
                redAway?.replace(";", "\n")
                val yellowAway: String? = data?.awayYellowCard
                yellowAway?.replace(";", "\n")
                val gkAway: String? = data?.awayGoalK
                gkAway?.replace(";", "\n")
                val defAway: String? = data?.awayDefense
                defAway?.replace(";", "\n")
                val midAway: String? = data?.awayMidfield
                midAway?.replace(";", "\n")
                val forAway: String? = data?.awayForward
                forAway?.replace(";", "\n")
                val subAway: String? = data?.awaySubstitutes
                subAway?.replace(";", "\n")

                Glide.with(ctx).load(homeData?.teamBadge).into(imgHome)
                homeManager.text = homeData?.teamManager
                txtGoalHome.text = goalHome
                txtShotsHome.text = data?.homeShots
                txtRedHome.text = redHome
                txtYellowHome.text = yellowHome
                txtGkHome.text = gkHome
                txtDefHome.text = defHome
                txtMidHome.text = midHome
                txtForHome.text = forHome
                txtSubHome.text = subHome

                Glide.with(ctx).load(awayData?.teamBadge).into(imgAway)
                homeManagerSide.text = awayData?.teamManager
                txtGoalAway.text = goalAway
                txtShotsAway.text = data?.awayShots
                txtRedAway.text = redAway
                txtYellowAway.text = yellowAway
                txtGkAway.text = gkAway
                txtDefAway.text = defAway
                txtMidAway.text = midAway
                txtForAway.text = forAway
                txtSubAway.text = subAway
            }
        } catch (e: Exception) {
            Log.d("TAG", e.toString())
        }
    }

    override fun showBadge() {
        Log.i("Away Badge", "${detailItems[0].awayGoals}")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) {
                    removeFromFavorite()
                } else {
                    addToFavorite()
                }

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE)
                    .whereArgs("(eventId = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE,
                        FavoriteMatch.homeTeamId to homeId,
                        FavoriteMatch.awayTeamId to awayId,
                        FavoriteMatch.scoreHome to goalHome,
                        FavoriteMatch.scoreAway to goalAway,
                        FavoriteMatch.teamHome to homeTeam.text,
                        FavoriteMatch.teamAway to homeTeamSide.text,
                        FavoriteMatch.dateMatch to txtDate.text,
                        FavoriteMatch.eventId to matchId)
            }
            val snackbar = Snackbar.make(
                    root_layout, // Parent view
                    R.string.add_to_favorit, // Message to show
                    Snackbar.LENGTH_LONG // How long to display the message.
            )
            val snack_root_view = snackbar.view
            // Change the snack bar root view background color
            snack_root_view.setBackgroundColor(Color.parseColor("#009688"))
            snackbar.show()

        } catch (e: SQLiteConstraintException) {
            Snackbar.make(
                    root_layout, // Parent view
                    e.localizedMessage, // Message to show
                    Snackbar.LENGTH_LONG // How long to display the message.
            ).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteMatch.TABLE_FAVORITE, "(eventId = {id})",
                        "id" to id)
            }
            val snackbar = Snackbar.make(
                    root_layout, // Parent view
                    R.string.remove_to_favorit, // Message to show
                    Snackbar.LENGTH_LONG // How long to display the message.
            )
            val snack_root_view = snackbar.view
            // Change the snack bar root view background color
            snack_root_view.setBackgroundColor(Color.parseColor("#b40505"))
            snackbar.show()
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
            Snackbar.make(
                    root_layout, // Parent view
                    e.localizedMessage, // Message to show
                    Snackbar.LENGTH_LONG // How long to display the message.
            ).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}
