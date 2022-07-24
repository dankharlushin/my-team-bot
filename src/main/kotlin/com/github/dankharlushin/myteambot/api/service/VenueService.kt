package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.model.Venue

interface VenueService {

    fun getVenueById(id: Int): Venue

    fun getVenuesByCountryId(countryId: Int): List<Venue>
}