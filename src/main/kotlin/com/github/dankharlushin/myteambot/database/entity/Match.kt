package com.github.dankharlushin.myteambot.database.entity

import com.github.dankharlushin.myteambot.api.model.ApiMatchStatus
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "match")
class Match(
    var matchStatus: ApiMatchStatus,
    var matchStart: LocalDateTime,
    var leagueId: Int,
    var seasonId: Int,
    @ManyToOne
    var homeTeam: Team,
    @ManyToOne
    var awayTeam: Team,
    @ManyToOne
    var venue: Venue?,
    @Id
    var id: Int
)