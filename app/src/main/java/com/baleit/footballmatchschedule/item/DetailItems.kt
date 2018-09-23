package com.baleit.footballmatchschedule.item

import com.google.gson.annotations.SerializedName

data class DetailItems(

        //Match Details HOME
        @SerializedName("strHomeGoalDetails") val homeGoals: String? = null,
        @SerializedName("strHomeRedCards") val homeRedCard: String? = null,
        @SerializedName("strHomeYellowCards") val homeYellowCard: String? = null,
        @SerializedName("strHomeLineupGoalkeeper") val homeGoalK: String? = null,
        @SerializedName("strHomeLineupDefense") val homeDefense: String? = null,
        @SerializedName("strHomeLineupMidfield") val homeMidfield: String? = null,
        @SerializedName("strHomeLineupForward") val homeForward: String? = null,
        @SerializedName("strHomeLineupSubstitutes") val homeSubstitutes: String? = null,
        @SerializedName("strHomeFormation") val homeForm: String? = null,
        @SerializedName("intHomeShots") val homeShots: String? = null,

        //Match Details AWAY
        @SerializedName("strAwayGoalDetails") val awayGoals: String? = null,
        @SerializedName("strAwayRedCards") val awayRedCard: String? = null,
        @SerializedName("strAwayYellowCards") val awayYellowCard: String? = null,
        @SerializedName("strAwayLineupGoalkeeper") val awayGoalK: String? = null,
        @SerializedName("strAwayLineupDefense") val awayDefense: String? = null,
        @SerializedName("strAwayLineupMidfield") val awayMidfield: String? = null,
        @SerializedName("strAwayLineupForward") val awayForward: String? = null,
        @SerializedName("strAwayLineupSubstitutes") val awaySubstitutes: String? = null,
        @SerializedName("strAwayFormation") val awayForm: String? = null,
        @SerializedName("intAwayShots") val awayShots: String? = null
)