package com.baleit.footballmatchschedule.adapter


import android.content.Context
import java.text.SimpleDateFormat
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.baleit.footballmatchschedule.item.MatchItems
import com.baleit.footballmatchschedule.R
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class BallAdapter(private val context: Context?, private var matchItems: List<MatchItems>, private val listener: (MatchItems) -> Unit)
    : RecyclerView.Adapter<BallAdapter.MatchViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(matchItems[position], listener)
    }

    override fun getItemCount(): Int {
        return matchItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {

        return MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.items, parent, false))
    }

    fun refresh(fill: List<MatchItems>) {
        matchItems = fill
        notifyDataSetChanged()
    }

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dateMatch = view.findViewById(R.id.date_match) as TextView
        private val homeTeam = view.findViewById(R.id.home_team) as TextView
        private val awayTeam = view.findViewById(R.id.away_team) as TextView
        private val scoreTeam = view.findViewById(R.id.score_team) as TextView

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindItem(matchItems: MatchItems?, listener: (MatchItems) -> Unit) {
            matchItems?.dateMatch?.let{
                dateMatch.text = matchItems?.dateMatch.toString().dateFormat()
                homeTeam.text = matchItems?.teamHome
                awayTeam.text = matchItems?.teamAway
                scoreTeam.text = matchItems?.scoreHome + " : " + matchItems?.scoreAway
                itemView.onClick { matchItems?.let { it1 -> listener(it1) } }

                if (scoreTeam.text.equals("null : null")) {
                    scoreTeam.text = " vs "
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun String.dateFormat(format: String? = "EEEE, dd MMMM yyyy"): String {
            val date: Date = SimpleDateFormat("dd/MM/yy").parse(this)
            return SimpleDateFormat(format, Locale("in", "ID")).format(date)
        }
    }

}