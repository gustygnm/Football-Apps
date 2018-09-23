package com.baleit.footballmatchschedule.respon

import com.baleit.footballmatchschedule.item.PlayersItems

data class PlayersResponse(
        val player: List<PlayersItems>)