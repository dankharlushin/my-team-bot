package com.github.dankharlushin.myteambot.api.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.model.ApiLeague
import com.github.dankharlushin.myteambot.api.service.ApiLeagueService
import org.springframework.stereotype.Service

@Service
class ApiLeagueServiceImpl(val restClient: RestClient): ApiLeagueService {
    override fun getLeagueById(id: Int): ApiLeague {
        return restClient.getEntity("/soccer/leagues/${id}", ApiLeague::class.java)
    }

    override fun getLeaguesByCountryId(countryId: Int): List<ApiLeague> {
        return restClient.getListEntity("/soccer/leagues?country_id=${countryId}", ApiLeague::class.java)
    }

    override fun getSubscribedLeagues(): List<ApiLeague> {
        return restClient.getListEntity("/soccer/leagues?subscribed=true", ApiLeague::class.java)
    }
}