package com.github.dankharlushin.myteambot.database

import com.github.dankharlushin.myteambot.api.ApiProperties
import com.github.dankharlushin.myteambot.api.model.ApiCountry
import com.github.dankharlushin.myteambot.api.model.ApiMatch
import com.github.dankharlushin.myteambot.api.model.ApiTeam
import com.github.dankharlushin.myteambot.api.model.ApiVenue
import com.github.dankharlushin.myteambot.api.service.ApiMatchService
import com.github.dankharlushin.myteambot.api.service.ApiSeasonService
import com.github.dankharlushin.myteambot.api.service.ApiTeamService
import com.github.dankharlushin.myteambot.database.entity.Country
import com.github.dankharlushin.myteambot.database.entity.Match
import com.github.dankharlushin.myteambot.database.entity.Team
import com.github.dankharlushin.myteambot.database.entity.Venue
import com.github.dankharlushin.myteambot.database.repository.CountryRepository
import com.github.dankharlushin.myteambot.database.repository.MatchRepository
import com.github.dankharlushin.myteambot.database.repository.TeamRepository
import com.github.dankharlushin.myteambot.database.repository.VenueRepository
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class DataFetcher(
    val matchService: ApiMatchService,
    val teamService: ApiTeamService,
    val seasonService: ApiSeasonService,
    val apiProperties: ApiProperties,
    val matchRepository: MatchRepository,
    val teamRepository: TeamRepository,
    val countryRepository: CountryRepository,
    val venueRepository: VenueRepository
) {

    companion object {
        private const val MATCHES_FETCH_INTERVAL: Long = 192 * 1000
    }

    @Scheduled(fixedDelay = MATCHES_FETCH_INTERVAL)
    fun fetchMatchesData() {
        matchService.getMatchesBySeasonId(seasonService.getCurrentSeason(apiProperties.leagueId).id)
            .map(this::mapMatch)
            .forEach(this::saveMatch)
    }

    private fun saveMatch(match: Match) {
        saveTeam(match.homeTeam)
        saveTeam(match.awayTeam)
        saveVenue(match.venue)

        matchRepository.save(match)
    }

    private fun saveVenue(venue: Venue?) {
        venue?.let {
            if (!venueRepository.existsById(venue.id))
                venueRepository.save(venue)
        }
    }

    private fun saveTeam(team: Team) {
        team.country?.let {
            if (!countryRepository.existsById(it.id))
                countryRepository.save(it)
        }
        teamRepository.save(team)
    }

    private fun mapTeam(apiTeam: ApiTeam): Team {
        return Team(id = apiTeam.id, name = apiTeam.name, country = mapCountry(apiTeam.country), logo = apiTeam.logo)
    }

    private fun mapCountry(apiCountry: ApiCountry?): Country? {
        return apiCountry?.let { return@let Country(id = it.id, name = it.name, continent = it.continent) }
    }

    private fun mapMatch(apiMatch: ApiMatch): Match {
        return Match(id = apiMatch.id, matchStatus = apiMatch.matchStatus, matchStart = apiMatch.matchStart,
            leagueId = apiMatch.leagueId, seasonId = apiMatch.seasonId, homeTeam = mapTeam(apiMatch.homeTeam),
            awayTeam = mapTeam(apiMatch.awayTeam), venue = mapVenue(apiMatch.venue))
    }

    private fun mapVenue(apiVenue: ApiVenue?): Venue? {
        return apiVenue?.let { return@let Venue(id = apiVenue.id, name = apiVenue.name, capacity = apiVenue.capacity,
            city = apiVenue.city, country = mapCountry(apiVenue.country)) }
    }

}