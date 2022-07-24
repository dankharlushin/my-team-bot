package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.model.Season

interface SeasonService {

    fun getSeasonById(id: Int): Season

    fun getSeasonsByLeagueId(leagueId: Int): List<Season>

    fun getCurrentSeason(leagueId: Int): Season
}