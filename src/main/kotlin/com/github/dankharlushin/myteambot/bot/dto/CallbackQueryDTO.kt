package com.github.dankharlushin.myteambot.bot.dto

import java.io.Serializable

data class CallbackQueryDTO(
    val queryId: String,
    val data: Map<String, Any>
): Serializable
