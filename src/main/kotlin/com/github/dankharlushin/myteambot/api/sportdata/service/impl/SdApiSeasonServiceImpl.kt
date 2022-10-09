package com.github.dankharlushin.myteambot.api.sportdata.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiSeason
import com.github.dankharlushin.myteambot.api.sportdata.service.SdApiSeasonService
import org.springframework.stereotype.Service

@Deprecated("bug in sportdata api")
@Service
class SdApiSeasonServiceImpl(val restClient: RestClient): SdApiSeasonService {
    override fun getSeasonById(id: Int): SdApiSeason {
        return restClient.getEntity("/soccer/seasons/${id}", SdApiSeason::class.java)
    }

    override fun getSeasonsByLeagueId(leagueId: Int): List<SdApiSeason> {
        return restClient.getListEntity("/soccer/seasons?league_id=${leagueId}", SdApiSeason::class.java)
    }

    override fun getCurrentSeason(leagueId: Int): SdApiSeason {//FIXME move
        return getSeasonsByLeagueId(leagueId).first { it.isCurrent }
    }
}