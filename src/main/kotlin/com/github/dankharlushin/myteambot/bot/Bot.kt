package com.github.dankharlushin.myteambot.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable

@Component
class Bot(val handlerManager: HandlerManager) : TelegramLongPollingBot() {

    @Value("\${bot.username}")
    lateinit var username: String

    @Value("\${bot.token}")
    lateinit var token: String

    override fun getBotToken() = token

    override fun getBotUsername() = username

    override fun <T : Serializable, Method : BotApiMethod<T>> execute(method: Method): T = super.execute(method)

    override fun onUpdateReceived(update: Update) {
        val handler = handlerManager.findHandler(update)
        handler.handle(update).forEach { execute(it) }
    }
}