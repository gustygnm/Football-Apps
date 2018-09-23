package com.baleit.footballmatchschedule.respon

import com.baleit.footballmatchschedule.item.TeamsItems

data class TeamsResponse(
        val teams: List<TeamsItems>)