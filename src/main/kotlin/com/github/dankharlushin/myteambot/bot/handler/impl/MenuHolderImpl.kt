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
        private const val MY_MATCHES_CODE = "myMatchesButtonDescription"
        private const val HELP_CODE = "helpButtonDescription"
    }

    val defaultCommands: Map<String, String> = mapOf(
        "/teams" to sourceAccessor.getMessage(ALL_TEAMS_CODE),
        "/myteams" to sourceAccessor.getMessage(MY_TEAMS_CODE),
        "/mymatches" to sourceAccessor.getMessage(MY_MATCHES_CODE),
        "/help" to sourceAccessor.getMessage(HELP_CODE)
    )

    override fun buildMenu(update: Update): SetMyCommands {
        val menu = SetMyCommands()
        menu.commands = defaultCommands.entries.map { BotCommand.builder().command(it.key).description(it.value).build() }
        return menu
    }
}