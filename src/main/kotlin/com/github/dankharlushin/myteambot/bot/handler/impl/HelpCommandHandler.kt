package com.github.dankharlushin.myteambot.bot.handler.impl

import com.github.dankharlushin.myteambot.bot.handler.TextMessageHandler
import com.vdurmont.emoji.EmojiParser
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class HelpCommandHandler(
    val sourceAccessor: MessageSourceAccessor
) : TextMessageHandler {

    companion object {
        private const val COMMAND = "/help"
        private const val HELP_MESSAGE_CODE = "helpMessage"
    }

    override fun getTextMessage() = COMMAND

    override fun handle(update: Update): List<BotApiMethod<*>> {
        val message = SendMessage.builder().chatId(update.message.chatId.toString()).text(
            EmojiParser.parseToUnicode(sourceAccessor.getMessage(HELP_MESSAGE_CODE))).build()
        return listOf(message)
    }
}