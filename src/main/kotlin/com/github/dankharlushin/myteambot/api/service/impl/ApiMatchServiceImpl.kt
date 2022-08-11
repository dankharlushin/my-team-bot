package com.github.dankharlushin.myteambot.api.service.impl

import com.github.dankharlushin.myteambot.api.ApiProperties
import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.model.ApiMatch
import com.github.dankharlushin.myteambot.api.service.ApiMatchService
import com.github.dankharlushin.myteambot.api.service.ApiSeasonService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ApiMatchServiceImpl(
    val restClient: RestClient,
    val apiProperties: ApiProperties,
    val seasonService: ApiSeasonService
): ApiMatchService {
    override fun getMatchById(id: Int): ApiMatch {
        return restClient.getEntity("/soccer/matches/${id}", ApiMatch::class.java)
    }

    override fun getMatchesBySeasonId(seasonId: Int): List<ApiMatch> {
        return restClient.getListEntity("/soccer/matches?season_id=${seasonId}", ApiMatch::class.java)
    }

    override fun getLiveMatches(): List<ApiMatch> {
        return restClient.getListEntity("/soccer/matches?live=true", ApiMatch::class.java)
    }

    override fun getMatchesByDate(seasonId: Int, dateFrom: LocalDate, dateTo: LocalDate): List<ApiMatch> {
        return restClient.getListEntity("/soccer/matches?season_id=${seasonId}&date_from=${dateFrom}&date_to=${dateTo}",
            ApiMatch::class.java)
    }

    override fun getMatchesByTeamIdAndDate(//FIXME move
        seasonId: Int?,
        teamId: Int,
        dateFrom: LocalDate,
        dateTo: LocalDate
    ): List<ApiMatch> {
        val matchesByDate = getMatchesByDate(seasonId ?: seasonService.getCurrentSeason(apiProperties.leagueId).id, dateFrom, dateTo)
        return matchesByDate.filter { it.homeTeam.id == teamId || it.awayTeam.id == teamId}
    }
}