package com.github.dankharlushin.myteambot.api.model

import com.fasterxml.jackson.annotation.JsonAlias

data class ApiTeam(
    @JsonAlias("team_id")
    var id: Int,
    var name: String,
    @JsonAlias("common_name")
    var commonName: String?,
    @JsonAlias("short_code")
    var shortCode: String,
    var logo: String,
    var country: ApiCountry?
)
