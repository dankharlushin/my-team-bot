package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.ApiTestConfiguration
import com.github.dankharlushin.myteambot.api.service.impl.SeasonServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDate

@SpringBootTest(
    classes = [SeasonServiceImpl::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ApiTestConfiguration::class)
@DirtiesContext
class SeasonServiceTest {

    @Autowired
    lateinit var seasonService: SeasonService

    @Test
    fun testGetSeasonById() {
        val season = seasonService.getSeasonById(1480)//TODO add property

        assertEquals(1480, season.id)
        assertEquals("19/20", season.name)
        assertEquals(false, season.isCurrent)
        assertEquals(102, season.countryId)
        assertEquals(504, season.leagueId)
        assertEquals(LocalDate.of(2019, 7, 12), season.startDate)
        assertEquals(LocalDate.of(2020, 7, 24), season.endDate)
    }

    @Test
    fun testGetSeasonsByLeagueId() {
        val seasons = seasonService.getSeasonsByLeagueId(504)//TODO add property

        assertTrue(seasons.size > 1)
        assertEquals(1477, seasons[0].id)
        assertEquals("20/21", seasons[0].name)
        assertEquals(false, seasons[0].isCurrent)
        assertEquals(102, seasons[0].countryId)
        assertEquals(504, seasons[0].leagueId)
        assertEquals(LocalDate.of(2020, 8, 8), seasons[0].startDate)
        assertEquals(LocalDate.of(2021, 5, 23), seasons[0].endDate)
    }
}