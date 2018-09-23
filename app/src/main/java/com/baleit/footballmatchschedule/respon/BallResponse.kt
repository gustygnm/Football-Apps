package com.baleit.footballmatchschedule.respon

import com.google.gson.annotations.SerializedName
import com.baleit.footballmatchschedule.item.MatchItems


data class BallResponse(
        @SerializedName("events") val matchItems: List<MatchItems>?)