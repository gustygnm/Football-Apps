package com.baleit.footballmatchschedule.item

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by root on 1/23/18.
 */
data class PlayersItems(
        @SerializedName("idPlayer")
        var idPlayer: String? = null,

        @SerializedName("strPlayer")
        var strPlayer: String? = null,

        @SerializedName("strCutout")
        var strCutout: String? = null,

        @SerializedName("strPosition")
        var strPosition: String? = null
        )