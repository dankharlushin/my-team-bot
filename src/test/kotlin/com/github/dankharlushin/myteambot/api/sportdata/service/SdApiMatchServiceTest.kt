package com.github.dankharlushin.myteambot.api.sportdata.service

import com.github.dankharlushin.myteambot.api.sportdata.SdApiProperties
import com.github.dankharlushin.myteambot.api.sportdata.SdApiTestConfiguration
import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiMatchStatus
import com.github.dankharlushin.myteambot.api.sportdata.service.impl.SdApiMatchServiceImpl
import com.github.dankharlushin.myteambot.api.sportdata.service.impl.SdApiSeasonServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Deprecated("bug in sportdata api")
@SpringBootTest(
    classes = [SdApiMatchServiceImpl::class, SdApiSeasonServiceImpl::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(SdApiTestConfiguration::class)
@EnableConfigurationProperties(SdApiProperties::class)
@DirtiesContext
class SdApiMatchServiceTest {

    @Autowired
    lateinit var matchService: SdApiMatchService

    @Test
    fun testGetMatchById() {
        val match = matchService.getMatchById(218614) //TODO add property

        assertEquals(218614, match.id)
        assertEquals(SdApiMatchStatus.ENDED, match.matchStatus)
        assertEquals("finished", match.status)
        assertEquals(LocalDateTime.of(2019, 7, 13, 13, 30), match.matchStart)
        assertEquals(1480, match.seasonId)

        assertEquals(6192, match.homeTeam.id)
        assertEquals("FC Ural Yekaterinburg", match.homeTeam.name)
        assertEquals("URA", match.homeTeam.shortCode)
        assertEquals("https://cdn.sportdataapi.com/images/soccer/teams/100/853.png", match.homeTeam.logo)
        assertEquals(102, match.homeTeam.country!!.id)
        assertEquals("Russia", match.homeTeam.country!!.name)
        assertEquals("ru", match.homeTeam.country!!.countryCode)
        assertEquals("Europe", match.homeTeam.country!!.continent)

        assertEquals(3480, match.awayTeam.id)
        assertEquals("FC Ufa", match.awayTeam.name)
        assertEquals("UFA", match.awayTeam.shortCode)
        assertEquals("https://cdn.sportdataapi.com/images/soccer/teams/100/849.png", match.awayTeam.logo)
        assertEquals(102, match.awayTeam.country!!.id)
        assertEquals("Russia", match.awayTeam.country!!.name)
        assertEquals("ru", match.awayTeam.country!!.countryCode)
        assertEquals("Europe", match.awayTeam.country!!.continent)

        assertEquals(0, match.stats.homeScore)
        assertEquals(0, match.stats.awayScore)
        assertEquals("1-0", match.stats.htScore)
        assertEquals("3-2", match.stats.ftScore)
        assertNull(match.stats.etScore)
        assertNull(match.stats.psScore)

        assertEquals(2811, match.venue!!.id)
        assertEquals("Central Stadium", match.venue!!.name)
        assertEquals(35061, match.venue!!.capacity)
        assertEquals("Ekaterinburg", match.venue!!.city)
        assertEquals(102, match.venue!!.countryId)
        assertNull(match.venue!!.country)
    }

    @Test
    fun testGetMatchesBySeasonId() {
        matchService.getLiveMatches()
        val matches = matchService.getMatchesBySeasonId(1480) //TODO add property

        assertTrue(matches.size > 1)
        assertEquals(218596, matches[0].id)
        assertEquals(SdApiMatchStatus.ENDED, matches[0].matchStatus)
        assertEquals("finished", matches[0].status)
        assertEquals(LocalDateTime.of(2019, 7, 12, 17, 0), matches[0].matchStart)
        assertEquals(1480, matches[0].seasonId)

        assertEquals(6197, matches[0].homeTeam.id)
        assertEquals("FC Arsenal Tula", matches[0].homeTeam.name)
        assertEquals("ART", matches[0].homeTeam.shortCode)
        assertEquals("https://cdn.sportdataapi.com/images/soccer/teams/100/856.png", matches[0].homeTeam.logo)
        assertEquals(102, matches[0].homeTeam.country!!.id)
        assertEquals("Russia", matches[0].homeTeam.country!!.name)
        assertEquals("ru", matches[0].homeTeam.country!!.countryCode)
        assertEquals("Europe", matches[0].homeTeam.country!!.continent)

        assertEquals(6180, matches[0].awayTeam.id)
        assertEquals("FC Dinamo Moscow", matches[0].awayTeam.name)
        assertEquals("DMO", matches[0].awayTeam.shortCode)
        assertEquals("https://cdn.sportdataapi.com/images/soccer/teams/100/845.png", matches[0].awayTeam.logo)
        assertEquals(102, matches[0].awayTeam.country!!.id)
        assertEquals("Russia", matches[0].awayTeam.country!!.name)
        assertEquals("ru", matches[0].awayTeam.country!!.countryCode)
        assertEquals("Europe", matches[0].awayTeam.country!!.continent)

        assertEquals(1, matches[0].stats.homeScore)
        assertEquals(1, matches[0].stats.awayScore)
        assertEquals("0-1", matches[0].stats.htScore)
        assertEquals("1-1", matches[0].stats.ftScore)
        assertNull(matches[0].stats.etScore)
        assertNull(matches[0].stats.psScore)

        assertEquals(2810, matches[0].venue!!.id)
        assertEquals("Arsenal Stadium", matches[0].venue!!.name)
        assertEquals(20074, matches[0].venue!!.capacity)
        assertEquals("Tula", matches[0].venue!!.city)
        assertEquals(102, matches[0].venue!!.countryId)
        assertNull(matches[0].venue!!.country)
    }

    @Test
    fun testGetMatchesByDate() {
        val dateFrom = LocalDate.of(2020, 7, 15)
        val dateTo = LocalDate.of(2020, 7, 21)
        val matches = matchService.getMatchesByDate(1480, dateFrom, dateTo)

        assertTrue(matches.size == 9)
        for (match in matches) {
            assertTrue(match.matchStart.isAfter(LocalDateTime.of(dateFrom, LocalTime.MIN)))
            assertTrue(match.matchStart.isBefore(LocalDateTime.of(dateTo, LocalTime.MIN)))
        }
    }

    @Test
    fun testGetMatchesByTeamIdAndDate() {
        val dateFrom = LocalDate.of(2022, 8, 6)
        val dateTo = LocalDate.of(2022, 8, 10)
        val matches = matchService.getMatchesByTeamIdAndDate(3283, 6198, dateFrom, dateTo)

        assertTrue(matches.size == 1)
        assertTrue(matches[0].matchStart.isAfter(LocalDateTime.of(dateFrom, LocalTime.MIN)))
        assertTrue(matches[0].matchStart.isBefore(LocalDateTime.of(dateTo, LocalTime.MIN)))
        assertEquals(matches[0].homeTeam.id, 6198)
    }
}