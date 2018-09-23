package com.baleit.footballmatchschedule.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.db.FavoriteMatch
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteMatchAdapter(private val context: Context?, private var favoriteMatch: List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit)
    : RecyclerView.Adapter<FavoriteMatchAdapter.FavoriteViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favoriteMatch[position], listener)
    }
    override fun getItemCount(): Int = favoriteMatch.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.items, parent, false))
    }

    fun refresh(fill: List<FavoriteMatch>) {
        favoriteMatch = fill
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dateMatch = view.findViewById(R.id.date_match) as TextView
        private val homeTeam = view.findViewById(R.id.home_team) as TextView
        private val awayTeam = view.findViewById(R.id.away_team) as TextView
        private val scoreTeam = view.findViewById(R.id.score_team) as TextView

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindItem(favoriteMatch: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {

            dateMatch.text = favoriteMatch.dateMatch.toString()
            homeTeam.text = favoriteMatch.teamHome
            awayTeam.text = favoriteMatch.teamAway
            scoreTeam.text = favoriteMatch.scoreHome + " : " + favoriteMatch.scoreAway
            itemView.onClick { listener(favoriteMatch) }

            if (scoreTeam.text.equals("null : null")) {
                scoreTeam.text = " vs "
            }
        }
    }
}