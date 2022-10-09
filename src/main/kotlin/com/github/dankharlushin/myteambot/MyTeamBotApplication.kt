package com.github.dankharlushin.myteambot

import com.github.dankharlushin.myteambot.api.sportdata.SdApiProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(SdApiProperties::class)
class MyTeamBotApplication

fun main(args: Array<String>) {
    runApplication<MyTeamBotApplication>(*args)
}
