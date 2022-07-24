package com.github.dankharlushin.myteambot.api.service

import com.github.dankharlushin.myteambot.api.model.Match
import java.time.LocalDate

interface MatchService {

    fun getMatchById(id: Int): Match

    fun getMatchesBySeasonId(seasonId: Int): List<Match>

    fun getLiveMatches(): List<Match>

    fun getMatchesByDate(seasonId: Int, dateFrom: LocalDate, dateTo: LocalDate): List<Match>

    fun getMatchesByTeamIdAndDate(seasonId: Int?, teamId: Int, dateFrom: LocalDate, dateTo: LocalDate): List<Match>
}