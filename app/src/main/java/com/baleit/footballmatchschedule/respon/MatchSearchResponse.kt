package com.baleit.footballmatchschedule.respon

import com.google.gson.annotations.SerializedName
import com.baleit.footballmatchschedule.item.MatchItems

data class MatchSearchResponse(
        @SerializedName("event") val matchItems: List<MatchItems>?)