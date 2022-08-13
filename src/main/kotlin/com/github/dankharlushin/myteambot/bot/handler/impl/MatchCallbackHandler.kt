package com.github.dankharlushin.myteambot.bot.handler.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.dankharlushin.myteambot.bot.SUBSCRIBE_MESSAGE_CODE
import com.github.dankharlushin.myteambot.bot.UNSUBSCRIBE_MESSAGE_CODE
import com.github.dankharlushin.myteambot.bot.dto.CallbackQueryDTO
import com.github.dankharlushin.myteambot.bot.handler.CallbackQueryHandler
import com.github.dankharlushin.myteambot.bot.inlineKeyboard
import com.github.dankharlushin.myteambot.database.entity.Match
import com.github.dankharlushin.myteambot.database.entity.Team
import com.github.dankharlushin.myteambot.database.repository.MatchRepository
import com.github.dankharlushin.myteambot.database.service.SubscriptionService
import com.vdurmont.emoji.EmojiParser.parseToUnicode
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import java.time.format.DateTimeFormatter

@Component
class MatchCallbackHandler(
    val matchRepository: MatchRepository,
    val objectMapper: ObjectMapper,
    val subscriptionService: SubscriptionService,
    val sourceAccessor: MessageSourceAccessor
): CallbackQueryHandler {

    companion object {
        private const val COL_NUM = 1
        private const val QUERY_ID = "match"
        private const val MATCH_ID = "matchId"
        private const val START_MESSAGE_CODE = "matchStart"
        private const val STADIUM_MESSAGE_CODE = "matchStadium"
        private const val CITY_MESSAGE_CODE = "city"
    }

    override fun getCallBackQueryId() = QUERY_ID

    @Transactional
    override fun handle(update: Update): List<BotApiMethod<*>> {
        val data = objectMapper.readValue(update.callbackQuery.data, CallbackQueryDTO::class.java).data
        val match = matchRepository.getById(data[MATCH_ID] as Int)

        val keyboardData: MutableList<Pair<String, CallbackQueryDTO>> = mutableListOf()
        keyboardData.add(teamData(match.homeTeam))
        keyboardData.add(teamData(match.awayTeam))
        keyboardData.add(initSubscriptionData(update.callbackQuery.from, match))

        val messageReply = SendMessage()
        messageReply.chatId = update.callbackQuery.message.chatId.toString()
        messageReply.text = constructMessageText(match)
        messageReply.replyMarkup = inlineKeyboard(keyboardData, COL_NUM)
        return listOf(messageReply)
    }

    private fun constructMessageText(match: Match): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm")

        val matchStart = parseToUnicode(sourceAccessor.getMessage(START_MESSAGE_CODE, arrayOf(formatter.format(match.matchStart))))
        val matchStadium = match.venue?.let {
            return@let parseToUnicode(sourceAccessor.getMessage(STADIUM_MESSAGE_CODE, arrayOf(it.name)))
        }
        val matchCity = match.venue?.city.let {
            return@let parseToUnicode(sourceAccessor.getMessage(CITY_MESSAGE_CODE, arrayOf(it)))
        }
        val teams = "${match.homeTeam.name.uppercase()} - ${match.awayTeam.name.uppercase()}"

        return "$teams\n" +
                "$matchStart\n" +
                "$matchStadium\n" +
                "$matchCity\n"
    }

    private fun initSubscriptionData(user: User, match: Match): Pair<String, CallbackQueryDTO> {
        var message = SUBSCRIBE_MESSAGE_CODE
        var subscribe = true
        if (subscriptionService.isSubscribed(user, match)) {
            message = UNSUBSCRIBE_MESSAGE_CODE
            subscribe = false
        }
        return Pair(parseToUnicode(sourceAccessor.getMessage(message)),
            CallbackQueryDTO(queryId = "subscription", data = mapOf("matchId" to match.id, "sub" to subscribe)))
    }

    private fun teamData(team: Team): Pair<String, CallbackQueryDTO> {
        return Pair(team.name, CallbackQueryDTO(queryId = "team", data = mapOf("teamId" to team.id)))
    }
}