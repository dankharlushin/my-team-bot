package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.model.ApiSeason

interface ApiSeasonService {

    fun getSeasonById(id: Int): ApiSeason

    fun getSeasonsByLeagueId(leagueId: Int): List<ApiSeason>

    fun getCurrentSeason(leagueId: Int): ApiSeason
}