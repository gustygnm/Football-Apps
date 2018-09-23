package com.baleit.footballmatchschedule.respon

import com.baleit.footballmatchschedule.item.DetailItems
import com.baleit.footballmatchschedule.item.PlayerDetailItems
import com.google.gson.annotations.SerializedName

data class PlayerDetailResponse(
        @SerializedName("players") val playerDetail: List<PlayerDetailItems>?)