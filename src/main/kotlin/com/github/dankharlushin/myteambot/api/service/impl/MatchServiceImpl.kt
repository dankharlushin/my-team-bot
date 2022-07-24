package com.github.dankharlushin.myteambot.api.service.impl

import com.github.dankharlushin.myteambot.api.ApiProperties
import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.model.Match
import com.github.dankharlushin.myteambot.api.service.MatchService
import com.github.dankharlushin.myteambot.api.service.SeasonService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MatchServiceImpl(
    val restClient: RestClient,
    val apiProperties: ApiProperties,
    val seasonService: SeasonService
): MatchService {
    override fun getMatchById(id: Int): Match {
        return restClient.getEntity("/soccer/matches/${id}", Match::class.java)
    }

    override fun getMatchesBySeasonId(seasonId: Int): List<Match> {
        return restClient.getListEntity("/soccer/matches?season_id=${seasonId}", Match::class.java)
    }

    override fun getLiveMatches(): List<Match> {
        return restClient.getListEntity("/soccer/matches?live=true", Match::class.java)
    }

    override fun getMatchesByDate(seasonId: Int, dateFrom: LocalDate, dateTo: LocalDate): List<Match> {
        return restClient.getListEntity("/soccer/matches?season_id=${seasonId}&date_from=${dateFrom}&date_to=${dateTo}",
            Match::class.java)
    }

    override fun getMatchesByTeamIdAndDate(
        seasonId: Int?,
        teamId: Int,
        dateFrom: LocalDate,
        dateTo: LocalDate
    ): List<Match> {
        val matchesByDate = getMatchesByDate(seasonId ?: seasonService.getCurrentSeason(apiProperties.leagueId).id, dateFrom, dateTo)
        return matchesByDate.filter { it.homeTeam.id == teamId || it.awayTeam.id == teamId}
    }
}