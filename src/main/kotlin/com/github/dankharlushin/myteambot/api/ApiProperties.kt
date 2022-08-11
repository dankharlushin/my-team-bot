package com.github.dankharlushin.myteambot.api

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "api")
data class ApiProperties(
    val leagueId: Int
)