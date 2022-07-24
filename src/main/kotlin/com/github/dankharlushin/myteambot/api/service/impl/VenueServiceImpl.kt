package com.github.dankharlushin.myteambot.api.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.model.Venue
import com.github.dankharlushin.myteambot.api.service.VenueService
import org.springframework.stereotype.Service

@Service
class VenueServiceImpl(val restClient: RestClient): VenueService {
    override fun getVenueById(id: Int): Venue {
        return restClient.getEntity("/soccer/venues/${id}", Venue::class.java)
    }

    override fun getVenuesByCountryId(countryId: Int): List<Venue> {
        return restClient.getListEntity("/soccer/venues?country_id=${countryId}", Venue::class.java)
    }
}