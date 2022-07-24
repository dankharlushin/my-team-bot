package com.github.dankharlushin.myteambot.bot.handler.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.dankharlushin.myteambot.api.model.Match
import com.github.dankharlushin.myteambot.api.service.MatchService
import com.github.dankharlushin.myteambot.api.service.TeamService
import com.github.dankharlushin.myteambot.bot.dto.CallbackQueryDTO
import com.github.dankharlushin.myteambot.bot.handler.CallbackQueryHandler
import com.github.dankharlushin.myteambot.bot.inlineKeyboard
import com.vdurmont.emoji.EmojiParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.time.LocalDate
import java.time.format.DateTimeFormatter




@Component
class TeamCallBackHandler(
    val matchService: MatchService,
    val teamService: TeamService,
    val objectMapper: ObjectMapper,
    val sourceAccessor: MessageSourceAccessor,
    @Value("\${defaults.matches.period}")
    val period: Long
): CallbackQueryHandler {

    companion object {
        private const val COL_NUM = 1
        private const val QUERY_ID = "team"
        private const val TEAM_ID = "teamId"
        private const val TEAM_NAME = "teamName"
        private const val REPLY_MESSAGE_CODE = "upcomingTeamMatches"
        private const val EMOJI = ":pushpin:"
    }

    override fun getCallBackQueryId() = QUERY_ID

    override fun handle(update: Update): List<BotApiMethod<*>> {
        val data = objectMapper.readValue(update.callbackQuery.data, CallbackQueryDTO::class.java).data
        val teamId = data[TEAM_ID] as Int
        val teamName = teamService.getTeamById(teamId).name//FIXME
        val comingMatches = matchService.getMatchesByTeamIdAndDate(
            null, teamId,
            LocalDate.now(), LocalDate.now().plusDays(period))

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