package com.github.dankharlushin.myteambot.api.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.model.Season
import com.github.dankharlushin.myteambot.api.service.SeasonService
import org.springframework.stereotype.Service

@Service
class SeasonServiceImpl(val restClient: RestClient): SeasonService {
    override fun getSeasonById(id: Int): Season {
        return restClient.getEntity("/soccer/seasons/${id}", Season::class.java)
    }

    override fun getSeasonsByLeagueId(leagueId: Int): List<Season> {
        return restClient.getListEntity("/soccer/seasons?league_id=${leagueId}", Season::class.java)
    }

    override fun getCurrentSeason(leagueId: Int): Season {
        return getSeasonsByLeagueId(leagueId).first { it.isCurrent }
    }
}