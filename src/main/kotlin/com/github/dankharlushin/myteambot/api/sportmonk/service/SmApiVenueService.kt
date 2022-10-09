package com.github.dankharlushin.myteambot.api.sportmonk.service

import com.sportmonks.data.entity.Venue

interface SmApiVenueService {

    fun getVenuesBySeasonId(seasonId: Long): List<Venue>
}