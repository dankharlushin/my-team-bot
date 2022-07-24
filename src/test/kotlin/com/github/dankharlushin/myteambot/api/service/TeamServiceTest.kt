package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.ApiTestConfiguration
import com.github.dankharlushin.myteambot.api.service.impl.TeamServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(
    classes = [TeamServiceImpl::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ApiTestConfiguration::class)
@DirtiesContext
class TeamServiceTest {

    @Autowired
    lateinit var teamService: TeamService

    @Test
    fun testGetTeamById() {
        var team = teamService.getTeamById(6184)//TODO add property

        assertEquals(6184, team.id)//TODO use assertThat?
        assertEquals("CSKA Moscow", team.name)
        assertEquals("", team.commonName)
        assertEquals("CSKA", team.shortCode)
        assertEquals("https://cdn.sportdataapi.com/images/soccer/teams/100/848.png", team.logo)
        assertEquals(102, team.country!!.id)
        assertEquals("Russia", team.country!!.name)
        assertEquals("ru", team.country!!.countryCode)
        assertEquals("Europe", team.country!!.continent)
    }

    @Test
    fun testGetTeamsByCountryId() {
        val teams = teamService.getTeamsByCountryId(102)//TODO add property

        assertTrue(teams.size > 1)
        assertEquals(3376, teams[0].id)
        assertEquals("CSKA Moscow U19", teams[0].name)
        assertEquals("", teams[0].commonName)
        assertEquals("CSM", teams[0].shortCode)
        assertEquals("https://cdn.sportdataapi.com/images/soccer/teams/100/17762.png", teams[0].logo)
        assertEquals(102, teams[0].country!!.id)
        assertEquals("Russia", teams[0].country!!.name)
        assertEquals("ru", teams[0].country!!.countryCode)
        assertEquals("Europe", teams[0].country!!.continent)
    }
}