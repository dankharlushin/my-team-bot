package com.github.dankharlushin.myteambot.bot.handler.impl

import com.github.dankharlushin.myteambot.bot.handler.MenuHolder
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand

@Component
class MenuHolderImpl(sourceAccessor: MessageSourceAccessor) : MenuHolder {

    companion object {
        private const val ALL_TEAMS_CODE = "allTeamsButtonDescription"
        private const val MY_TEAMS_CODE = "myTeamsButtonDescription"
    }

    val defaultCommands: Map<String, String> = mapOf(
        "/teams" to sourceAccessor.getMessage(ALL_TEAMS_CODE),
        "/favorites" to sourceAccessor.getMessage(MY_TEAMS_CODE)
    )

    override fun buildMenu(update: Update): SetMyCommands {
        val menu = SetMyCommands()
        menu.commands = defaultCommands.entries.map { BotCommand.builder().command(it.key).description(it.value).build() }
        return menu
    }
}