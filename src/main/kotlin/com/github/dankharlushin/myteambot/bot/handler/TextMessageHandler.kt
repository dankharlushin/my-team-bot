package com.github.dankharlushin.myteambot.bot.handler

interface TextMessageHandler: UpdateHandler {

    fun getTextMessage(): String
}