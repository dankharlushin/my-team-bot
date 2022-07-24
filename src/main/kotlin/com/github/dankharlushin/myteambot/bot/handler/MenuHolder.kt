package com.github.dankharlushin.myteambot.bot.handler

import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.objects.Update

interface MenuHolder {

    fun buildMenu(update: Update): SetMyCommands
}