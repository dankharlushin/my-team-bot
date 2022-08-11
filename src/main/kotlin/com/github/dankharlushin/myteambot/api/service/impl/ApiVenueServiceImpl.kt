package com.github.dankharlushin.myteambot.api.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.model.ApiVenue
import com.github.dankharlushin.myteambot.api.service.ApiVenueService
import org.springframework.stereotype.Service

@Service
class ApiVenueServiceImpl(val restClient: RestClient): ApiVenueService {
    override fun getVenueById(id: Int): ApiVenue {
        return restClient.getEntity("/soccer/venues/${id}", ApiVenue::class.java)
    }

    override fun getVenuesByCountryId(countryId: Int): List<ApiVenue> {
        return restClient.getListEntity("/soccer/venues?country_id=${countryId}", ApiVenue::class.java)
    }
}