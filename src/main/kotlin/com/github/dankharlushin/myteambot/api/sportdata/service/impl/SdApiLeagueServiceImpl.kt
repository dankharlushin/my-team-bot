package com.github.dankharlushin.myteambot.api.sportdata.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiLeague
import com.github.dankharlushin.myteambot.api.sportdata.service.SdApiLeagueService
import org.springframework.stereotype.Service

@Deprecated("bug in sportdata api")
@Service
class SdApiLeagueServiceImpl(val restClient: RestClient): SdApiLeagueService {
    override fun getLeagueById(id: Int): SdApiLeague {
        return restClient.getEntity("/soccer/leagues/${id}", SdApiLeague::class.java)
    }

    override fun getLeaguesByCountryId(countryId: Int): List<SdApiLeague> {
        return restClient.getListEntity("/soccer/leagues?country_id=${countryId}", SdApiLeague::class.java)
    }

    override fun getSubscribedLeagues(): List<SdApiLeague> {
        return restClient.getListEntity("/soccer/leagues?subscribed=true", SdApiLeague::class.java)
    }
}