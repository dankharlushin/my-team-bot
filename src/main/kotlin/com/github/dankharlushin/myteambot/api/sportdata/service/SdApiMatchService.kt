package com.github.dankharlushin.myteambot.api.sportdata.service

import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiMatch
import java.time.LocalDate

@Deprecated("bug in sportdata api")
interface SdApiMatchService {

    fun getMatchById(id: Int): SdApiMatch

    fun getMatchesBySeasonId(seasonId: Int): List<SdApiMatch>

    fun getLiveMatches(): List<SdApiMatch>

    fun getMatchesByDate(seasonId: Int, dateFrom: LocalDate, dateTo: LocalDate): List<SdApiMatch>

    fun getMatchesByTeamIdAndDate(seasonId: Int?, teamId: Int, dateFrom: LocalDate, dateTo: LocalDate): List<SdApiMatch>
}