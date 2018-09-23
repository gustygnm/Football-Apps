package com.baleit.footballmatchschedule.respon

import com.google.gson.annotations.SerializedName
import com.baleit.footballmatchschedule.item.DetailItems

data class DetailResponse(
        @SerializedName("events") val detailItems: List<DetailItems>?
)