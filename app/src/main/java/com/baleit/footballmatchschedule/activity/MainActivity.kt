package com.baleit.footballmatchschedule.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.R.id.*
import com.baleit.footballmatchschedule.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                match -> {
                    loadPastMatch(savedInstanceState)
                }
                teams -> {
                    loadTeams(savedInstanceState)
                }
                favorit -> {
                    loadFavorit(savedInstanceState)
                }
                tentang_app -> {
                    loadTentang(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = match
    }

    private fun loadPastMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchFrag(), MatchFrag::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadTeams(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamsFrag(), TeamsFrag::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadFavorit(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoritFrag(), FavoritFrag::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadTentang(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TentangFrag(), TentangFrag::class.java.simpleName)
                    .commit()
        }
    }

    internal var doubleBackToExit = false
    override fun onBackPressed() {
        if (doubleBackToExit) {
            finishAffinity()
        } else {
            Toast.makeText(this, R.string.pesan_keluar, Toast.LENGTH_SHORT).show()
        }
        val timee = 2000
        this.doubleBackToExit = true
        Handler().postDelayed({ doubleBackToExit = false }, timee.toLong())
    }
}
