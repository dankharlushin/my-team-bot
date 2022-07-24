package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.model.League

interface LeagueService {

    fun getLeagueById(id: Int): League

    fun getLeaguesByCountryId(countryId: Int): List<League>

    fun getSubscribedLeagues(): List<League>
}