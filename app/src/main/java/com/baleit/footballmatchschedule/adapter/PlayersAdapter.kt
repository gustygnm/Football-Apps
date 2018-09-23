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
import com.baleit.footballmatchschedule.item.PlayersItems
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayersAdapter(private val context: Context?, private var teams: List<PlayersItems>, private val listener: (PlayersItems) -> Unit)
    : RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_player, parent, false))
    }

    fun refresh(fill: List<PlayersItems>) {
        teams = fill
        notifyDataSetChanged()
    }

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.findViewById(R.id.image) as ImageView
        private val name = view.findViewById(R.id.name) as TextView
        private val posisi = view.findViewById(R.id.posisi) as TextView

        fun bindItem(player: PlayersItems, listener: (PlayersItems) -> Unit) {
            Picasso.get().load(player.strCutout).into(image)
            name.text = player.strPlayer
            posisi.text = player.strPosition
            itemView.onClick { listener(player) }
        }
    }
}