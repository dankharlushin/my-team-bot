package com.github.dankharlushin.myteambot.database.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "match")
class Match(
    var matchStart: LocalDateTime,
    var matchStatus: String,
    var leagueId: Long,
    var seasonId: Long,
    @ManyToOne
    var homeTeam: Team,
    @ManyToOne
    var awayTeam: Team,
    @ManyToOne
    var venue: Venue?,
    @Id
    var id: Long
)