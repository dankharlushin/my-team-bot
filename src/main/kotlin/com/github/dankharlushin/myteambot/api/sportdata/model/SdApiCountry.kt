package com.github.dankharlushin.myteambot.api.sportdata.model

import com.fasterxml.jackson.annotation.JsonAlias

@Deprecated("bug in sportdata api")
data class SdApiCountry(
    @JsonAlias("country_id")
    var id: Int,
    var name: String,
    @JsonAlias("country_code")
    var countryCode: String? = null,
    var continent: String
)
