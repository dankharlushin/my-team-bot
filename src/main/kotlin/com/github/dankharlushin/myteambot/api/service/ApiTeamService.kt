package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.model.ApiTeam

interface ApiTeamService {

    fun getTeamsByCountryId(countryId: Int): List<ApiTeam>

    fun getTeamById(teamId: Int): ApiTeam

    fun getTeamByName(name: String): ApiTeam
}