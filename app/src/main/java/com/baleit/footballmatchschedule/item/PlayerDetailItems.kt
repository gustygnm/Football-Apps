package com.baleit.footballmatchschedule.item

import com.google.gson.annotations.SerializedName
import java.util.*

data class PlayerDetailItems(

        @SerializedName("strPlayer")
        var strPlayer: String? = null,

        @SerializedName("strFanart1")
        var strFanart1: String? = null,

        @SerializedName("strHeight")
        var strHeight: String? = null,

        @SerializedName("strWeight")
        var strWeight: String? = null,

        @SerializedName("strPosition")
        var strPosition: String? = null,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String? = null
)