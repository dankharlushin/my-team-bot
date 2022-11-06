package com.github.dankharlushin.myteambot.database.event

import com.github.dankharlushin.myteambot.database.entity.MatchStatus

class ChangeMatchStatusEvent(
    source: Any,
    val matchId: Long,
    val oldStatus: MatchStatus,
    val newStatus: MatchStatus,
    val homeTeam: String,
    val visitorTeam: String,
    val afterEventScore: Pair<Int, Int>//TODO move parameters to parent class
) : AbstractMatchEvent(source)