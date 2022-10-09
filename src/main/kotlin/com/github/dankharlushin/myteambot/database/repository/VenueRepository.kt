package com.github.dankharlushin.myteambot.database.repository

import com.github.dankharlushin.myteambot.database.entity.Venue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VenueRepository : JpaRepository<Venue, Long>