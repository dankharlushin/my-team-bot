package com.github.dankharlushin.myteambot.api.sportdata.service

import com.github.dankharlushin.myteambot.api.sportdata.SdApiTestConfiguration
import com.github.dankharlushin.myteambot.api.sportdata.service.impl.SdApiLeagueServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext

@Deprecated("bug in sportdata api")
@SpringBootTest(
    classes = [SdApiLeagueServiceImpl::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(SdApiTestConfiguration::class)
@DirtiesContext
class SdApiLeagueServiceTest {

    @Autowired
    lateinit var leagueService: SdApiLeagueService

    @Test
    fun testGetLeagueById() {
        val league = leagueService.getLeagueById(505)

        assertEquals(505, league.id)
        assertEquals(102, league.countryId)
        assertEquals("FNL Cup", league.name)
    }

    @Test
    fun testGetLeagueByCountryId() {
        val leagues = leagueService.getLeaguesByCountryId(102)//TODO add property

        assertTrue(leagues.size > 1)
        assertEquals(504, leagues[0].id)
        assertEquals(102, leagues[0].countryId)
        assertEquals("Premier League", leagues[0].name)
    }

    @Test
    fun testGetSubscribedLeagues() {
        val leagues = leagueService.getSubscribedLeagues()

        assertTrue(leagues.size == 2)

        assertEquals(237, leagues[0].id)
        assertEquals(42, leagues[0].countryId)
        assertEquals("Premier League", leagues[0].name)

        assertEquals(504, leagues[1].id)
        assertEquals(102, leagues[1].countryId)
        assertEquals("Premier League", leagues[1].name)
    }
}