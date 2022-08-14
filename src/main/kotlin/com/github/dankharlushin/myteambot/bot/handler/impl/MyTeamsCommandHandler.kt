package com.github.dankharlushin.myteambot.bot.handler.impl

import com.github.dankharlushin.myteambot.bot.dto.CallbackQueryDTO
import com.github.dankharlushin.myteambot.bot.handler.TextMessageHandler
import com.github.dankharlushin.myteambot.bot.inlineKeyboard
import com.github.dankharlushin.myteambot.database.repository.SubscriberRepository
import com.vdurmont.emoji.EmojiParser
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class MyTeamsCommandHandler(
    val subscriberRepository: SubscriberRepository,
    val sourceAccessor: MessageSourceAccessor
) : TextMessageHandler {

    companion object {
        private const val COL_NUM = 1
        private const val COMMAND = "/myteams"
        private const val MY_TEAMS_MESSAGE_CODE = "myTeams"
        private const val DEFAULT_MESSAGE_CODE = "emptySubscribedTeamList"
    }

    override fun getTextMessage() = COMMAND

    @Transactional
    override fun handle(update: Update): List<BotApiMethod<*>> {
        val subscriber = subscriberRepository.findById(update.message.from.id.toInt())
        if (subscriber.isEmpty || subscriber.get().teams.isEmpty())
            return listOf(initReplyMessage(update.message.chatId, DEFAULT_MESSAGE_CODE))

        val teams = subscriber.get().teams
        val keyboardData: MutableList<Pair<String, CallbackQueryDTO>> = mutableListOf()
        for (team in teams) {
            val dto = CallbackQueryDTO(queryId = "team", data = mapOf("teamId" to team.id))
            val info = team.name
            keyboardData.add(Pair(info, dto))
        }

        val message = initReplyMessage(update.message.chatId, MY_TEAMS_MESSAGE_CODE)
        message.replyMarkup = inlineKeyboard(keyboardData, COL_NUM)
        return listOf(message)
    }

    private fun initReplyMessage(chatId: Long, messageCode: String): SendMessage {
        return SendMessage.builder().chatId(chatId.toString()).text(
            EmojiParser.parseToUnicode(sourceAccessor.getMessage(messageCode))).build()
    }
}