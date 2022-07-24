package com.github.dankharlushin.myteambot.api.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.model.League
import com.github.dankharlushin.myteambot.api.service.LeagueService
import org.springframework.stereotype.Service

@Service
class LeagueServiceImpl(val restClient: RestClient): LeagueService {
    override fun getLeagueById(id: Int): League {
        return restClient.getEntity("/soccer/leagues/${id}", League::class.java)
    }

    override fun getLeaguesByCountryId(countryId: Int): List<League> {
        return restClient.getListEntity("/soccer/leagues?country_id=${countryId}", League::class.java)
    }

    override fun getSubscribedLeagues(): List<League> {
        return restClient.getListEntity("/soccer/leagues?subscribed=true", League::class.java)
    }
}