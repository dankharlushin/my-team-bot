package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.ApiTestConfiguration
import com.github.dankharlushin.myteambot.api.service.impl.LeagueServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(
    classes = [LeagueServiceImpl::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ApiTestConfiguration::class)
@DirtiesContext
class LeagueServiceTest {

    @Autowired
    lateinit var leagueService: LeagueService

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

        assertEquals(504, leagues[0].id)
        assertEquals(102, leagues[0].countryId)
        assertEquals("Premier League", leagues[0].name)

        assertEquals(693, leagues[1].id)
        assertEquals(25, leagues[1].countryId)
        assertEquals("Brasileiro Serie A", leagues[1].name)
    }
}