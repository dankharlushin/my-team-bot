package com.github.dankharlushin.myteambot.bot.handler.impl

import com.github.dankharlushin.myteambot.bot.handler.MenuHolder
import com.github.dankharlushin.myteambot.bot.handler.TextMessageHandler
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class StartCommandHandler(
    val sourceAccessor: MessageSourceAccessor,
    val menuHolder: MenuHolder
) : TextMessageHandler {

    companion object {
        private const val COMMAND = "/start"
        private const val REPLY_MESSAGE_CODE = "startMessage"
    }

    override fun getTextMessage() = COMMAND

    override fun handle(update: Update): List<BotApiMethod<*>> {
        val messageReply = SendMessage.builder()
            .chatId(update.message.chatId.toString())
            .text(initText(update))
            .build()
        return listOf(messageReply, menuHolder.buildMenu(update))
    }

    private fun initText(update: Update) =
        sourceAccessor.getMessage(REPLY_MESSAGE_CODE, arrayOf(update.message.from.firstName))
}