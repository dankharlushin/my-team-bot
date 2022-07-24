package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.model.Team

interface TeamService {

    fun getTeamsByCountryId(countryId: Int): List<Team>

    fun getTeamById(teamId: Int): Team

    fun getTeamByName(name: String): Team
}