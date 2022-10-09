package com.github.dankharlushin.myteambot.api.sportdata.model

import com.fasterxml.jackson.annotation.JsonAlias

@Deprecated("bug in sportdata api")
data class SdApiTeam(
    @JsonAlias("team_id")
    var id: Int,
    var name: String,
    @JsonAlias("common_name")
    var commonName: String?,
    @JsonAlias("short_code")
    var shortCode: String,
    var logo: String,
    var country: SdApiCountry?
)
