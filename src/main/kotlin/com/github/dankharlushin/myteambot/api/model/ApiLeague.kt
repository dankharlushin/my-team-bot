package com.github.dankharlushin.myteambot.api.model

import com.fasterxml.jackson.annotation.JsonAlias

data class ApiLeague(
    @JsonAlias("league_id")
    var id: Int,
    @JsonAlias("country_id")
    var countryId: Int,
    var name: String
)