package com.baleit.footballmatchschedule.respon

import com.google.gson.annotations.SerializedName
import com.baleit.footballmatchschedule.item.TeamItems

data class TeamResponse(
        @SerializedName("teams") val teamsItems: List<TeamItems>?
)