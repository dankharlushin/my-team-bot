package com.github.dankharlushin.myteambot.bot

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.dankharlushin.myteambot.bot.handler.CallbackQueryHandler
import com.github.dankharlushin.myteambot.bot.handler.TextMessageHandler
import com.github.dankharlushin.myteambot.bot.handler.UpdateHandler
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class HandlerManager(
    val queryHandlers: List<CallbackQueryHandler>,
    val textHandlers: List<TextMessageHandler>,
    val objectMapper: ObjectMapper
) {

    companion object {
        private const val QUERY_ID_MAPPING = "queryId"
    }

    fun findHandler(update: Update): UpdateHandler {
        return when {
            update.hasCallbackQuery() -> findQueryHandler(update)
            hasTextMessage(update) -> findTextHandler(update)
            else -> throw UnsupportedOperationException()
        }
    }

    private fun findTextHandler(update: Update): UpdateHandler {
        return textHandlers.find { update.message.text == it.getTextMessage() } ?: throw UnsupportedOperationException()
    }

    private fun findQueryHandler(update: Update): UpdateHandler {
        val response = objectMapper.readValue(update.callbackQuery.data, Map::class.java)
        return queryHandlers.find { response[QUERY_ID_MAPPING] == it.getCallBackQueryId() }
            ?: throw UnsupportedOperationException()
    }

    private fun hasTextMessage(update: Update) =  update.hasMessage() && update.message.hasText()
}