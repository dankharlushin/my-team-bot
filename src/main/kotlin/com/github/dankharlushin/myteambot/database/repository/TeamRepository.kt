package com.github.dankharlushin.myteambot.database.repository

import com.github.dankharlushin.myteambot.database.entity.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : JpaRepository<Team, Int>