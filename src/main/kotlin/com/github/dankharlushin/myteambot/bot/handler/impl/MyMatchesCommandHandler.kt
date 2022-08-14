package com.github.dankharlushin.myteambot.bot.handler.impl

import com.github.dankharlushin.myteambot.bot.dto.CallbackQueryDTO
import com.github.dankharlushin.myteambot.bot.handler.TextMessageHandler
import com.github.dankharlushin.myteambot.bot.inlineKeyboard
import com.github.dankharlushin.myteambot.database.entity.Match
import com.github.dankharlushin.myteambot.database.repository.SubscriberRepository
import com.vdurmont.emoji.EmojiParser
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.time.format.DateTimeFormatter

@Component
class MyMatchesCommandHandler(
    val subscriberRepository: SubscriberRepository,
    val sourceAccessor: MessageSourceAccessor
) : TextMessageHandler {

    companion object {
        private const val COL_NUM = 1
        private const val COMMAND = "/mymatches"
        private const val MY_TEAMS_MESSAGE_CODE = "myMatches"
        private const val DEFAULT_MESSAGE_CODE = "emptySubscribedMatchList"
        private const val EMOJI = ":pushpin:"
    }

    override fun getTextMessage() = COMMAND

    @Transactional
    override fun handle(update: Update): List<BotApiMethod<*>> {
        val subscriber = subscriberRepository.findById(update.message.from.id.toInt())
        if (subscriber.isEmpty || subscriber.get().matches.isEmpty())
            return listOf(initReplyMessage(update.message.chatId, DEFAULT_MESSAGE_CODE))

        val matches = subscriber.get().matches
        val keyboardData: MutableList<Pair<String, CallbackQueryDTO>> = mutableListOf()
        for (match in matches) {
            val dto = CallbackQueryDTO(queryId = "match", data = mapOf("matchId" to match.id))
            val info = initInfo(match)
            keyboardData.add(Pair(info, dto))
        }

        val message = initReplyMessage(update.message.chatId, MY_TEAMS_MESSAGE_CODE)
        message.replyMarkup = inlineKeyboard(keyboardData, COL_NUM)
        return listOf(message)
    }

    private fun initInfo(match: Match): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm")
        return EmojiParser.parseToUnicode(
            "$EMOJI ${formatter.format(match.matchStart)} | ${match.homeTeam.name} - ${match.awayTeam.name}")
    }

    private fun initReplyMessage(chatId: Long, messageCode: String): SendMessage {
        return SendMessage.builder().chatId(chatId.toString()).text(
            EmojiParser.parseToUnicode(sourceAccessor.getMessage(messageCode))).build()
    }
}