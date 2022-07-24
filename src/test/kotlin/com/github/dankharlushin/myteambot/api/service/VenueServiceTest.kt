package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.ApiTestConfiguration
import com.github.dankharlushin.myteambot.api.service.impl.VenueServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(
    classes = [VenueServiceImpl::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ApiTestConfiguration::class)
@DirtiesContext
class VenueServiceTest {

    @Autowired
    lateinit var venueService: VenueService

    @Test
    fun testGetVenueById() {
        val venue = venueService.getVenueById(1591)//TODO add property

        assertEquals(1591, venue.id)
        assertEquals("Stadium Oktyabr", venue.name)
        assertEquals(3060, venue.capacity)
        assertEquals("Moscow", venue.city)
        assertNull(venue.countryId)
        assertEquals(102, venue.country!!.id)
        assertEquals("Russia", venue.country!!.name)
        assertEquals("ru", venue.country!!.countryCode)
        assertEquals("Europe", venue.country!!.continent)
    }

    @Test
    fun testGetVenuesByCountryId() {
        val venues = venueService.getVenuesByCountryId(102)//TODO add property

        assertTrue(venues.size > 1)
        assertEquals(1672, venues[3].id)
        assertEquals("Neftyanik Stadium", venues[3].name)
        assertEquals(15234, venues[3].capacity)
        assertEquals("Ufa", venues[3].city)
        assertEquals(102, venues[3].countryId)
        assertNull(venues[3].country)
    }
}