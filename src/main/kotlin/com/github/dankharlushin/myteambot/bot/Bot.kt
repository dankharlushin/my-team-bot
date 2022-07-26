package com.github.dankharlushin.myteambot.bot

import com.github.dankharlushin.myteambot.database.event.SendMessageEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.Serializable

@Component
class Bot(val handlerManager: HandlerManager) : TelegramLongPollingBot() {

    companion object {
        private val LOG = LoggerFactory.getLogger(Bot::class.java)
    }

    @Value("\${bot.username}")
    lateinit var username: String

    @Value("\${bot.token}")
    lateinit var token: String

    override fun getBotToken() = token

    override fun getBotUsername() = username

    override fun <T : Serializable, Method : BotApiMethod<T>> execute(method: Method): T = super.execute(method)

    override fun onUpdateReceived(update: Update) {
        try {
            val handler = handlerManager.findHandler(update)
            handler.handle(update).forEach { execute(it) }
        } catch (e: Exception) {
            LOG.error("Can't send answer in chat with id: '${update.message.chatId}'", e)
        }
    }

    @EventListener
    fun sendMessageByEvent(sendMessageEvent: SendMessageEvent) {
        val message = SendMessage.builder().chatId(sendMessageEvent.chatId).text(sendMessageEvent.message).build()
        try {
            execute(message)
        } catch (e: TelegramApiException) {
            LOG.error("Can't send event message in chat with id: '${sendMessageEvent.chatId}'")
        }
    }
}