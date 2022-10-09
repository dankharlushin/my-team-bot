package com.github.dankharlushin.myteambot.api.sportdata.model

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

@Deprecated("bug in sportdata api")
data class SdApiSeason(
    @JsonAlias("season_id")
    var id: Int,
    var name: String,
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonAlias("is_current")
    var isCurrent: Boolean,
    @JsonAlias("country_id")
    var countryId: Int,
    @JsonAlias("league_id")
    var leagueId: Int,
    @JsonAlias("start_date")
    var startDate: LocalDate,
    @JsonAlias("end_date")
    var endDate: LocalDate
)
