package com.github.dankharlushin.myteambot.api.sportdata.service

import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiTeam

@Deprecated("bug in sportdata api")
interface SdApiTeamService {

    fun getTeamsByCountryId(countryId: Int): List<SdApiTeam>

    fun getTeamById(teamId: Int): SdApiTeam
}