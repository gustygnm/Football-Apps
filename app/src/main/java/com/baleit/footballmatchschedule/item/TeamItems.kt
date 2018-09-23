package com.baleit.footballmatchschedule.item

import com.google.gson.annotations.SerializedName

data class TeamItems(
        //TeamsItems Details
        @SerializedName("strTeamBadge") val teamBadge: String? = null,
        @SerializedName("strManager") val teamManager: String? = null
)