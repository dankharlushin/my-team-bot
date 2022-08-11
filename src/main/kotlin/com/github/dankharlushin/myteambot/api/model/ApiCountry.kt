package com.github.dankharlushin.myteambot.api.model

import com.fasterxml.jackson.annotation.JsonAlias

data class ApiCountry(
    @JsonAlias("country_id")
    var id: Int,
    var name: String,
    @JsonAlias("country_code")
    var countryCode: String? = null,
    var continent: String
)
