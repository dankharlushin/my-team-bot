package com.github.dankharlushin.myteambot.bot.handler

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateHandler {

    fun handle(update: Update): List<BotApiMethod<*>>

}