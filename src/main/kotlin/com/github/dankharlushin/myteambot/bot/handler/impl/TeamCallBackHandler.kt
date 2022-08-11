package com.github.dankharlushin.myteambot.bot.handler.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.dankharlushin.myteambot.bot.dto.CallbackQueryDTO
import com.github.dankharlushin.myteambot.bot.handler.CallbackQueryHandler
import com.github.dankharlushin.myteambot.bot.inlineKeyboard
import com.github.dankharlushin.myteambot.database.entity.Match
import com.github.dankharlushin.myteambot.database.repository.MatchRepository
import com.github.dankharlushin.myteambot.database.repository.TeamRepository
import com.vdurmont.emoji.EmojiParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Component
class TeamCallBackHandler(
    val matchRepository: MatchRepository,
    val teamRepository: TeamRepository,
    val objectMapper: ObjectMapper,
    val sourceAccessor: MessageSourceAccessor,
    @Value("\${defaults.matches.period}")
    val period: Long
): CallbackQueryHandler {

    companion object {
        private const val COL_NUM = 1
        private const val QUERY_ID = "team"
        private const val TEAM_ID = "teamId"
        private const val REPLY_MESSAGE_CODE = "upcomingTeamMatches"
        private const val EMOJI = ":pushpin:"
    }

    override fun getCallBackQueryId() = QUERY_ID

    @Transactional
    override fun handle(update: Update): List<BotApiMethod<*>> {
        val data = objectMapper.readValue(update.callbackQuery.data, CallbackQueryDTO::class.java).data
        val teamId = data[TEAM_ID] as Int
        val teamName = teamRepository.getById(teamId).name
        val comingMatches = matchRepository
            .getTeamMatchesByPeriod(teamId, LocalDateTime.now(), LocalDateTime.now().plusDays(period))

        val keyboardData: MutableList<Pair<String, CallbackQueryDTO>> = mutableListOf()
        for (match in comingMatches) {
            val dto = CallbackQueryDTO(queryId = "match", data = mapOf("matchId" to match.id))
            val info = initInfo(match)
            keyboardData.add(Pair(info, dto))
        }

        val messageReply = SendMessage()
        messageReply.chatId = update.callbackQuery.message.chatId.toString()
        messageReply.text = sourceAccessor.getMessage(REPLY_MESSAGE_CODE, arrayOf(teamName))
        messageReply.replyMarkup = inlineKeyboard(keyboardData, COL_NUM)
        return listOf(messageReply)
    }

    private fun initInfo(match: Match): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm")
        return EmojiParser.parseToUnicode(
            "$EMOJI ${formatter.format(match.matchStart)} | ${match.homeTeam.name} - ${match.awayTeam.name}")
    }
}