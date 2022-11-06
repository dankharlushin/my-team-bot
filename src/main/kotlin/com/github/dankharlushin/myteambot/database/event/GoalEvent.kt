package com.github.dankharlushin.myteambot.database.event

class GoalEvent(
    source: Any,
    val matchId: Long,
    val homeTeam: String,
    val visitorTeam: String,
    val goalStatus: GoalStatus,
    val afterEventScore: Pair<Int, Int>//TODO move parameters to parent class
) : AbstractMatchEvent(source)