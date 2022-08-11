package com.github.dankharlushin.myteambot.bot.handler.impl

import com.github.dankharlushin.myteambot.bot.dto.CallbackQueryDTO
import com.github.dankharlushin.myteambot.bot.handler.TextMessageHandler
import com.github.dankharlushin.myteambot.bot.inlineKeyboard
import com.github.dankharlushin.myteambot.database.repository.TeamRepository
import com.vdurmont.emoji.EmojiParser
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class TeamsCommandHandler(
    val sourceAccessor: MessageSourceAccessor,
    val teamRepository: TeamRepository
) : TextMessageHandler {

    companion object {
        private const val COL_NUM = 2
        private const val COMMAND = "/teams"
        private const val REPLY_MESSAGE_CODE = "teamsMessage"
    }

    override fun getTextMessage() = COMMAND

    override fun handle(update: Update): List<BotApiMethod<*>> {
        val messageReply = SendMessage()
        messageReply.chatId = update.message.chatId.toString()
        messageReply.text = initText(update)

        val teams = teamRepository.findAll()
        val keyboardData: MutableList<Pair<String, CallbackQueryDTO>> = mutableListOf()
        for (team in teams) {
            val dto = CallbackQueryDTO(queryId = "team", data = mapOf("teamId" to team.id))
            val info = team.name
            keyboardData.add(Pair(info, dto))
        }

        messageReply.replyMarkup = inlineKeyboard(keyboardData, COL_NUM)
        return listOf(messageReply)
    }

    private fun initText(update: Update) =
        EmojiParser.parseToUnicode(
            sourceAccessor.getMessage(
                REPLY_MESSAGE_CODE,
                arrayOf(update.message.from.firstName)
            )
        )
}