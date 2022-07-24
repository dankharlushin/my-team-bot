package com.github.dankharlushin.myteambot.api.model

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime

data class Match(
    @JsonAlias("match_id")
    var id: Int,
    @JsonAlias("status_code")
    var matchStatus: MatchStatus,
    var status: String,
    @JsonAlias("match_start")
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var matchStart: LocalDateTime,
    @JsonAlias("league_id")
    var leagueId: Int,
    @JsonAlias("season_id")
    var seasonId: Int,
    @JsonAlias("home_team")
    var homeTeam: Team,
    @JsonAlias("away_team")
    var awayTeam: Team,
    var stats: Stats,
    var venue: Venue?
) {
    data class Stats(
        @JsonAlias("home_score")
        var homeScore: Int,
        @JsonAlias("away_score")
        var awayScore: Int,
        @JsonAlias("ht_score")
        var htScore: String?,
        @JsonAlias("ft_score")
        var ftScore: String?,
        @JsonAlias("et_score")
        var etScore: String?,
        @JsonAlias("ps_score")
        var psScore: String?
    )
}

