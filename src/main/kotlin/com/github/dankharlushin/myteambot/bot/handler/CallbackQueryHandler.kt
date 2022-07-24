package com.github.dankharlushin.myteambot.bot.handler

interface CallbackQueryHandler: UpdateHandler {

    fun getCallBackQueryId(): String
}