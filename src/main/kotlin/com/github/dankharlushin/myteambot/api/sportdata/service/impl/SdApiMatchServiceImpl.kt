package com.github.dankharlushin.myteambot.api.sportdata.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.sportdata.SdApiProperties
import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiMatch
import com.github.dankharlushin.myteambot.api.sportdata.service.SdApiMatchService
import com.github.dankharlushin.myteambot.api.sportdata.service.SdApiSeasonService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Deprecated("bug in sportdata api")
@Service
class SdApiMatchServiceImpl(
    val restClient: RestClient,
    val apiProperties: SdApiProperties,
    val seasonService: SdApiSeasonService
): SdApiMatchService {
    override fun getMatchById(id: Int): SdApiMatch {
        return restClient.getEntity("/soccer/matches/${id}", SdApiMatch::class.java)
    }

    override fun getMatchesBySeasonId(seasonId: Int): List<SdApiMatch> {
        return restClient.getListEntity("/soccer/matches?season_id=${seasonId}", SdApiMatch::class.java)
    }

    override fun getLiveMatches(): List<SdApiMatch> {
        return restClient.getListEntity("/soccer/matches?live=true", SdApiMatch::class.java)
    }

    override fun getMatchesByDate(seasonId: Int, dateFrom: LocalDate, dateTo: LocalDate): List<SdApiMatch> {
        return restClient.getListEntity("/soccer/matches?season_id=${seasonId}&date_from=${dateFrom}&date_to=${dateTo}",
            SdApiMatch::class.java)
    }

    override fun getMatchesByTeamIdAndDate(//FIXME move
        seasonId: Int?,
        teamId: Int,
        dateFrom: LocalDate,
        dateTo: LocalDate
    ): List<SdApiMatch> {
        val matchesByDate = getMatchesByDate(seasonId ?: seasonService.getCurrentSeason(apiProperties.leagueId).id, dateFrom, dateTo)
        return matchesByDate.filter { it.homeTeam.id == teamId || it.awayTeam.id == teamId}
    }
}