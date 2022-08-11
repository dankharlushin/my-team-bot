package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.model.ApiMatch
import java.time.LocalDate

interface ApiMatchService {

    fun getMatchById(id: Int): ApiMatch

    fun getMatchesBySeasonId(seasonId: Int): List<ApiMatch>

    fun getLiveMatches(): List<ApiMatch>

    fun getMatchesByDate(seasonId: Int, dateFrom: LocalDate, dateTo: LocalDate): List<ApiMatch>

    fun getMatchesByTeamIdAndDate(seasonId: Int?, teamId: Int, dateFrom: LocalDate, dateTo: LocalDate): List<ApiMatch>
}