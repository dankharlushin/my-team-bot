package com.github.dankharlushin.myteambot.database.repository

import com.github.dankharlushin.myteambot.database.entity.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime

@Repository
interface MatchRepository : JpaRepository<Match, Long> {

    @Query(value = "select m from Match m where (m.homeTeam.id = :teamId or m.awayTeam.id = :teamId)")
    fun getTeamMatches(@Param("teamId") teamId: Long): List<Match>

    @Query(value = "select m from Match m " +
            "where (m.homeTeam.id = :teamId or m.awayTeam.id = :teamId) " +
            "and m.matchStart between :dateFrom and :dateTo")
    fun getTeamMatchesByPeriod(@Param("teamId") teamId: Long,
                               @Param("dateFrom") dateFrom: ZonedDateTime,
                               @Param("dateTo") dateTo: ZonedDateTime): List<Match>
}