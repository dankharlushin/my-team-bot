package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.model.ApiVenue

interface ApiVenueService {

    fun getVenueById(id: Int): ApiVenue

    fun getVenuesByCountryId(countryId: Int): List<ApiVenue>
}