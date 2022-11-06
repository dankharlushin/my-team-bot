package com.github.dankharlushin.myteambot.bot.handler.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.dankharlushin.myteambot.bot.dto.CallbackQueryDTO
import com.github.dankharlushin.myteambot.bot.exception.SubscriptionException
import com.github.dankharlushin.myteambot.bot.handler.CallbackQueryHandler
import com.github.dankharlushin.myteambot.database.repository.MatchRepository
import com.github.dankharlushin.myteambot.database.repository.TeamRepository
import com.github.dankharlushin.myteambot.database.service.SubscriptionService
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class SubscriptionCallbackHandler(
    val subscriptionService: SubscriptionService,
    val matchRepository: MatchRepository,
    val teamRepository: TeamRepository,
    val objectMapper: ObjectMapper,
    val sourceAccessor: MessageSourceAccessor
) : CallbackQueryHandler {

    companion object {
        private const val QUERY_ID = "sub"
        private const val MATCH_KEY = "matchId"
        private const val TEAM_KEY = "teamId"
        private const val SUBSCRIBE_KEY = "sub"
        private const val SUBSCRIBE_MATCH_MESSAGE_CODE = "successfulMatchSubscription"
        private const val SUBSCRIBE_TEAM_MESSAGE_CODE = "successfulTeamSubscription"
        private const val UNSUBSCRIBE_MATCH_MESSAGE_CODE = "successfulMatchUnsubscribe"
        private const val UNSUBSCRIBE_TEAM_MESSAGE_CODE = "successfulTeamUnsubscribe"
    }

    override fun getCallBackQueryId() = QUERY_ID

    @Transactional
    override fun handle(update: Update): List<BotApiMethod<*>> {
        val data = objectMapper.readValue(update.callbackQuery.data, CallbackQueryDTO::class.java).data

        return when {
            data.containsKey(MATCH_KEY) -> handleMatchSubscription(update.callbackQuery, data[MATCH_KEY] as Long, data[SUBSCRIBE_KEY] as Boolean)
            data.containsKey(TEAM_KEY) -> handleTeamSubscription(update.callbackQuery, data[TEAM_KEY] as Long, data[SUBSCRIBE_KEY] as Boolean)
            else -> throw SubscriptionException()
        }
    }

    private fun handleTeamSubscription(
        callbackQuery: CallbackQuery,
        teamId: Long,
        subscribe: Boolean
    ): List<BotApiMethod<*>> {
        if (subscribe) {
            subscriptionService.subscribe(callbackQuery.from, teamRepository.getById(teamId), callbackQuery.message.chatId)
            return listOf(constructMessage(callbackQuery.message.chatId, sourceAccessor.getMessage(SUBSCRIBE_TEAM_MESSAGE_CODE)))
        }

        subscriptionService.unsubscribe(callbackQuery.from, teamRepository.getById(teamId))
        return  listOf(constructMessage(callbackQuery.message.chatId, sourceAccessor.getMessage(
            UNSUBSCRIBE_TEAM_MESSAGE_CODE)))
    }

    private fun handleMatchSubscription(
        callbackQuery: CallbackQuery,
        matchId: Long,
        subscribe: Boolean
    ): List<BotApiMethod<*>> {
        if (subscribe) {
            subscriptionService.subscribe(callbackQuery.from, matchRepository.getById(matchId), callbackQuery.message.chatId)
            return listOf(constructMessage(callbackQuery.message.chatId, sourceAccessor.getMessage(SUBSCRIBE_MATCH_MESSAGE_CODE)))
        }

        subscriptionService.unsubscribe(callbackQuery.from, matchRepository.getById(matchId))
        return  listOf(constructMessage(callbackQuery.message.chatId, sourceAccessor.getMessage(
            UNSUBSCRIBE_MATCH_MESSAGE_CODE)))
    }

    private fun constructMessage(chatId: Long, text: String): SendMessage {
        return SendMessage.builder().chatId(chatId.toString()).text(text).build()
    }
}