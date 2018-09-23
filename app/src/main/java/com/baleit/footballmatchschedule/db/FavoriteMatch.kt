package com.baleit.footballmatchschedule.db

data class FavoriteMatch(val id: Long?, val homeTeamId: String?, val awayTeamId: String?, val scoreHome: String?,
                         val scoreAway: String?, val teamHome: String?, val teamAway: String?, val dateMatch: String?,
                         val eventId: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val homeTeamId: String = "homeTeamId"
        const val awayTeamId: String = "awayTeamId"
        const val scoreHome: String = "scoreHome"
        const val scoreAway: String = "scoreAway"
        const val teamHome: String = "teamHome"
        const val teamAway: String = "teamAway"
        const val dateMatch: String = "dateMatch"
        const val eventId: String = "eventId"
    }
}