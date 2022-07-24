package com.github.dankharlushin.myteambot.api.model

import com.fasterxml.jackson.annotation.JsonAlias

data class League(
    @JsonAlias("league_id")
    var id: Int,
    @JsonAlias("country_id")
    var countryId: Int,
    var name: String
)