package com.github.dankharlushin.myteambot.api.sportmonk.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.sportmonk.service.SmApiVenueService
import com.sportmonks.data.entity.Venue
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SmApiVenueServiceImpl(
    val restClient: RestClient,
    @Value("\${api.token}") val apiToken: String
) : SmApiVenueService {

    override fun getVenuesBySeasonId(seasonId: Long): List<Venue> {
        return restClient.getListEntity("/venues/season/${seasonId}?api_token=${apiToken}", Venue::class.java)
    }
}