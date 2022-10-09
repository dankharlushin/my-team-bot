package com.github.dankharlushin.myteambot.api.sportdata.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiVenue
import com.github.dankharlushin.myteambot.api.sportdata.service.SdApiVenueService
import org.springframework.stereotype.Service

@Deprecated("bug in sportdata api")
@Service
class SdApiVenueServiceImpl(val restClient: RestClient): SdApiVenueService {
    override fun getVenueById(id: Int): SdApiVenue {
        return restClient.getEntity("/soccer/venues/${id}", SdApiVenue::class.java)
    }

    override fun getVenuesByCountryId(countryId: Int): List<SdApiVenue> {
        return restClient.getListEntity("/soccer/venues?country_id=${countryId}", SdApiVenue::class.java)
    }
}