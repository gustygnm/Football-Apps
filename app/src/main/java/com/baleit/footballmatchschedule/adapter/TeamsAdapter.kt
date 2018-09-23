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
import com.baleit.footballmatchschedule.item.TeamsItems
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by root on 1/16/18.
 */


class TeamsAdapter(private val context: Context?, private var teams: List<TeamsItems>, private val listener: (TeamsItems) -> Unit)
    : RecyclerView.Adapter<TeamsAdapter.MatchViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_teams, parent, false))
    }

    fun refresh(fill: List<TeamsItems>) {
        teams = fill
        notifyDataSetChanged()
    }

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val teamBadge = view.findViewById(R.id.image) as ImageView
        private val teamName = view.findViewById(R.id.name) as TextView

        fun bindItem(teams: TeamsItems, listener: (TeamsItems) -> Unit) {
            Picasso.get().load(teams.teamBadge).into(teamBadge)
            teamName.text = teams.teamName
            itemView.onClick { listener(teams) }
        }
    }
}