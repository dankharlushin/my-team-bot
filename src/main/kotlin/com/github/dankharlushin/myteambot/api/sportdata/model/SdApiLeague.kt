package com.github.dankharlushin.myteambot.api.sportdata.model

import com.fasterxml.jackson.annotation.JsonAlias

@Deprecated("bug in sportdata api")
data class SdApiLeague(
    @JsonAlias("league_id")
    var id: Int,
    @JsonAlias("country_id")
    var countryId: Int,
    var name: String
)