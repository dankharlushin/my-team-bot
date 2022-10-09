package com.github.dankharlushin.myteambot.api.sportdata.service

import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiLeague

@Deprecated("bug in sportdata api")
interface SdApiLeagueService {

    fun getLeagueById(id: Int): SdApiLeague

    fun getLeaguesByCountryId(countryId: Int): List<SdApiLeague>

    fun getSubscribedLeagues(): List<SdApiLeague>
}