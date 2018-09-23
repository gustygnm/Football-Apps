package com.baleit.footballmatchschedule.activity

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.baleit.footballmatchschedule.R.drawable.ic_add_to_favorites
import com.baleit.footballmatchschedule.R.drawable.ic_added_to_favorites
import com.baleit.footballmatchschedule.R.id.add_to_favorite
import com.baleit.footballmatchschedule.R.menu.detail_menu
import com.baleit.footballmatchschedule.api.ApiRepo
import com.baleit.footballmatchschedule.db.FavoriteTeams
import com.baleit.footballmatchschedule.db.database
import com.baleit.footballmatchschedule.item.TeamsItems
import com.baleit.footballmatchschedule.pagerAdapter.MyPagerAdapterDetailTeam
import com.baleit.footballmatchschedule.util.invisible
import com.baleit.footballmatchschedule.util.visible
import com.baleit.footballmatchschedule.presenter.TeamDetailPresenter
import com.baleit.footballmatchschedule.view.TeamDetailView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import com.baleit.footballmatchschedule.R


class TeamDetailActivity : AppCompatActivity(), TeamDetailView {
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: TeamsItems
    private var tabLayout: TabLayout? = null

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        val intent = intent
        id = intent.getStringExtra("id")
        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        tabLayout = findViewById(R.id.tablayoutFavorit) as TabLayout
        val viewPager = findViewById(R.id.viewpager_favorit) as ViewPager
        val adapter = MyPagerAdapterDetailTeam(supportFragmentManager, tabLayout!!.getTabCount())
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        favoriteState()
        val request = ApiRepo()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeams.TABLE_FAVORITE)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeams>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        progressBar.visible()
        appBarLayout.invisible()
        tablayoutFavorit.invisible()
        nestScrollview.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        appBarLayout.visible()
        tablayoutFavorit.visible()
        nestScrollview.visible()
    }

    override fun showTeamDetail(data: List<TeamsItems>) {
        teams = TeamsItems(data[0].teamId,
                data[0].teamName,
                data[0].teamBadge)
        Picasso.get().load(data[0].teamBadge).into(teamBadge)
        teamName.text = data[0].teamName
//        teamDescription.text = data[0].teamDescription
        teamFormedYear.text = data[0].teamFormedYear
        teamStadium.text = data[0].teamStadium

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
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteTeams.TABLE_FAVORITE,
                        FavoriteTeams.TEAM_ID to teams.teamId,
                        FavoriteTeams.TEAM_NAME to teams.teamName,
                        FavoriteTeams.TEAM_BADGE to teams.teamBadge)
            }
            val snackbar = Snackbar.make(
                    rootLayoutTeam, // Parent view
                    R.string.add_to_favorit, // Message to show
                    Snackbar.LENGTH_LONG // How long to display the message.
            )
            val snack_root_view = snackbar.view
            // Change the snack bar root view background color
            snack_root_view.setBackgroundColor(Color.parseColor("#009688"))
            snackbar.show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(
                    rootLayoutTeam, // Parent view
                    e.localizedMessage, // Message to show
                    Snackbar.LENGTH_LONG // How long to display the message.
            ).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteTeams.TABLE_FAVORITE, "(TEAM_ID = {id})",
                        "id" to id)
            }
            val snackbar = Snackbar.make(
                    rootLayoutTeam, // Parent view
                    R.string.remove_to_favorit, // Message to show
                    Snackbar.LENGTH_LONG // How long to display the message.
            )
            val snack_root_view = snackbar.view
            // Change the snack bar root view background color
            snack_root_view.setBackgroundColor(Color.parseColor("#b40505"))
            snackbar.show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(
                    rootLayoutTeam, // Parent view
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