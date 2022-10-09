package com.github.dankharlushin.myteambot.api.sportdata.service

import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiSeason

@Deprecated("bug in sportdata api")
interface SdApiSeasonService {

    fun getSeasonById(id: Int): SdApiSeason

    fun getSeasonsByLeagueId(leagueId: Int): List<SdApiSeason>

    fun getCurrentSeason(leagueId: Int): SdApiSeason
}