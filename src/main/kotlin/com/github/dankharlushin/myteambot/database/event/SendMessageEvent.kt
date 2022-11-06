package com.github.dankharlushin.myteambot.database.event

import org.springframework.context.ApplicationEvent

class SendMessageEvent(
    source: Any,
    val message: String,
    val chatId: String
) : ApplicationEvent(source)