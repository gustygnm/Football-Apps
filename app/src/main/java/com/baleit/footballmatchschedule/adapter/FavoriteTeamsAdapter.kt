package com.baleit.footballmatchschedule.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.db.FavoriteTeams
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteTeamsAdapter(private val context: Context?, private var teams: List<FavoriteTeams>, private val listener: (FavoriteTeams) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamsAdapter.FavoriteViewHolder>() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_teams, parent, false))
    }

    fun refresh(fill: List<FavoriteTeams>) {
        teams = fill
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val teamBadge = view.findViewById(R.id.image) as ImageView
        private val teamName = view.findViewById(R.id.name) as TextView

        fun bindItem(teams: FavoriteTeams, listener: (FavoriteTeams) -> Unit) {
            Picasso.get().load(teams.teamBadge).into(teamBadge)
            teamName.text = teams.teamName
            itemView.onClick { listener(teams) }
        }
    }
}