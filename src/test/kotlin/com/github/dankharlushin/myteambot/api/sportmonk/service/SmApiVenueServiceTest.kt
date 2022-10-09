package com.github.dankharlushin.myteambot.api.sportmonk.service

import com.github.dankharlushin.myteambot.api.sportmonk.SmApiTestConfiguration
import com.github.dankharlushin.myteambot.api.sportmonk.service.impl.SmApiVenueServiceImpl
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(
    classes = [SmApiVenueServiceImpl::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(SmApiTestConfiguration::class)
@DirtiesContext
class SmApiVenueServiceTest {

    @Autowired
    lateinit var venueService: SmApiVenueService

    @Test
    fun testGetFixtureById() {
        val venuesId = venueService.getVenuesBySeasonId(19735).map { it.id }

        assertTrue(venuesId.contains(8909))
        assertTrue(venuesId.contains(8914))
        assertTrue(venuesId.contains(8946))
        assertTrue(venuesId.contains(8908))
        assertTrue(venuesId.contains(8943))
        assertTrue(venuesId.contains(8928))
        assertTrue(venuesId.contains(8947))
        assertTrue(venuesId.contains(8922))
        assertTrue(venuesId.contains(336296))
        assertTrue(venuesId.contains(8879))
        assertTrue(venuesId.contains(219))
        assertTrue(venuesId.contains(340226))//falls because of bug in sportmonks api
    }
}