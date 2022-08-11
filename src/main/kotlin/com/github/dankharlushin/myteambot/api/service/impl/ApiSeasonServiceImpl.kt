package com.github.dankharlushin.myteambot.api.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.model.ApiSeason
import com.github.dankharlushin.myteambot.api.service.ApiSeasonService
import org.springframework.stereotype.Service

@Service
class ApiSeasonServiceImpl(val restClient: RestClient): ApiSeasonService {
    override fun getSeasonById(id: Int): ApiSeason {
        return restClient.getEntity("/soccer/seasons/${id}", ApiSeason::class.java)
    }

    override fun getSeasonsByLeagueId(leagueId: Int): List<ApiSeason> {
        return restClient.getListEntity("/soccer/seasons?league_id=${leagueId}", ApiSeason::class.java)
    }

    override fun getCurrentSeason(leagueId: Int): ApiSeason {//FIXME move
        return getSeasonsByLeagueId(leagueId).first { it.isCurrent }
    }
}