package com.github.dankharlushin.myteambot.database.repository

import com.github.dankharlushin.myteambot.database.entity.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface MatchRepository : JpaRepository<Match, Int> {

    @Query(value = "select m from Match m " +
            "where (m.homeTeam.id = :teamId or m.awayTeam.id = :teamId) " +
            "and m.matchStart between :dateFrom and :dateTo")
    fun getTeamMatchesByPeriod(@Param("teamId") teamId: Int,
                               @Param("dateFrom") dateFrom: LocalDateTime,
                               @Param("dateTo") dateTo: LocalDateTime): List<Match>
}