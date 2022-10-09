package com.github.dankharlushin.myteambot.api.sportdata

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@Deprecated("bug in sportdata api")
@ConstructorBinding
@ConfigurationProperties(prefix = "api")
data class SdApiProperties(
    val leagueId: Int
)