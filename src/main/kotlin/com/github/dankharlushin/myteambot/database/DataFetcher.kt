package com.github.dankharlushin.myteambot.database

import com.github.dankharlushin.myteambot.api.sportmonk.service.SmApiVenueService
import com.github.dankharlushin.myteambot.database.entity.Match
import com.github.dankharlushin.myteambot.database.entity.MatchStatus
import com.github.dankharlushin.myteambot.database.entity.Team
import com.github.dankharlushin.myteambot.database.entity.Venue
import com.github.dankharlushin.myteambot.database.repository.MatchRepository
import com.github.dankharlushin.myteambot.database.repository.TeamRepository
import com.github.dankharlushin.myteambot.database.repository.VenueRepository
import com.sportmonks.APIClient
import com.sportmonks.data.entity.Fixture
import com.sportmonks.endpoints.FixturesEndPointParams
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Component
@EnableScheduling
class DataFetcher(
    val venueService: SmApiVenueService,
    val matchRepository: MatchRepository,
    val teamRepository: TeamRepository,
    val venueRepository: VenueRepository,
    @Value("\${api.token}") val apiToken: String,
    @Value("\${api.league-id}") val leagueId: Long, //FIXME can be more than one league
    @Value("\${defaults.matches.period}") val daysPeriod: Long
) {

    companion object {
        private const val MATCHES_FETCH_INTERVAL: Long = 30 * 1000//FIXME can be least
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    @Scheduled(fixedDelay = MATCHES_FETCH_INTERVAL)
    fun fetchMatchesData() {
        val matchesByDateRange = APIClient.getInstance(apiToken).fixturesEndPointInstance.findByDateRange(
            Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(daysPeriod)), FixturesEndPointParams()
        )
        val currentSeasonId = currentSeason()
        val teams = APIClient.getInstance(apiToken).teamsEndPointInstance.findBySeason(currentSeasonId)
        val venues = venueService.getVenuesBySeasonId(currentSeasonId)
        matchesByDateRange.filter { leagueId == it.leagueId }.map { mapMatch(it, teams, venues) }.forEach(this::saveMatch)
    }

    private fun currentSeason(): Long {
        return APIClient.getInstance(apiToken).seasonsEndPointInstance.findAll().find {
            leagueId == it.leagueId && it.currentSeason }!!.id //FIXME can be more than one league
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
        teamRepository.save(team)
    }

    private fun mapTeam(apiTeam: com.sportmonks.data.entity.Team): Team {
        return Team(id = apiTeam.id,
            name = apiTeam.name,
            logo = apiTeam.logoPath)
    }

    private fun mapMatch(
        apiMatch: Fixture,
        teams: MutableList<com.sportmonks.data.entity.Team>,
        venues: List<com.sportmonks.data.entity.Venue>
    ): Match {
        return Match(id = apiMatch.id,
            matchStatus = MatchStatus.valueOf(apiMatch.time.status),
            matchStart = ZonedDateTime.of(LocalDateTime.parse(apiMatch.time.startingAt.dateTime, formatter), ZoneId.of(apiMatch.time.startingAt.timezone)),
            localTeamScore = apiMatch.scores.localTeamScore,
            visitorTeamScore = apiMatch.scores.visitorTeamScore,
            leagueId = apiMatch.leagueId,
            seasonId = apiMatch.seasonId,
            homeTeam = mapTeam(teams.first { apiMatch.localteamId == it.id }),
            awayTeam = mapTeam(teams.first { apiMatch.visitorteamId == it.id }),
            venue = mapVenue(venues.firstOrNull { apiMatch.venueId == it.id }))
    }

    private fun mapVenue(apiVenue: com.sportmonks.data.entity.Venue?): Venue? {
        return apiVenue?.let {
            return@let Venue(id = apiVenue.id,
            name = apiVenue.name,
            capacity = apiVenue.capacity,
            city = apiVenue.city) }
    }

}