package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.model.ApiLeague

interface ApiLeagueService {

    fun getLeagueById(id: Int): ApiLeague

    fun getLeaguesByCountryId(countryId: Int): List<ApiLeague>

    fun getSubscribedLeagues(): List<ApiLeague>
}