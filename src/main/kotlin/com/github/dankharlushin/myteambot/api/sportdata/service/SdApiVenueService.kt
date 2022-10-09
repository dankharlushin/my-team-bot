package com.github.dankharlushin.myteambot.api.sportdata.service

import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiVenue

@Deprecated("bug in sportdata api")
interface SdApiVenueService {

    fun getVenueById(id: Int): SdApiVenue

    fun getVenuesByCountryId(countryId: Int): List<SdApiVenue>
}