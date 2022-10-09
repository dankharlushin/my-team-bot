package com.github.dankharlushin.myteambot.api.sportdata.model

import com.fasterxml.jackson.annotation.JsonAlias

@Deprecated("bug in sportdata api")
data class SdApiVenue(
    @JsonAlias("venue_id")
    var id: Int,
    var name: String,
    var capacity: Int,
    var city: String,
    @JsonAlias("country_id")
    var countryId: Int?,
    var country: SdApiCountry?
)
