package com.github.dankharlushin.myteambot.api.model

import com.fasterxml.jackson.annotation.JsonAlias

data class ApiVenue(
    @JsonAlias("venue_id")
    var id: Int,
    var name: String,
    var capacity: Int,
    var city: String,
    @JsonAlias("country_id")
    var countryId: Int?,
    var country: ApiCountry?
)
