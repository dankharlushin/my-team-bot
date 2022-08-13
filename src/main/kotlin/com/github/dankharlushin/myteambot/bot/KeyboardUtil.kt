package com.github.dankharlushin.myteambot.bot

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.dankharlushin.myteambot.bot.dto.CallbackQueryDTO
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

const val SUBSCRIBE_MESSAGE_CODE = "subscribe"
const val UNSUBSCRIBE_MESSAGE_CODE = "unsubscribe"
private val objectMapper: ObjectMapper = ObjectMapper()

fun inlineKeyboard(data: List<Pair<String, CallbackQueryDTO>>, colNum: Int): InlineKeyboardMarkup {
    val keyboardMarkup = InlineKeyboardMarkup()
    val keyboard: MutableList<MutableList<InlineKeyboardButton>> = mutableListOf()
    val row: MutableList<InlineKeyboardButton> = mutableListOf()

    for (pair in data) {
        val button = initButton(pair)
        insertButton(colNum, button, row, keyboard)
    }
    keyboard.add(row)
    keyboardMarkup.keyboard = keyboard

    return keyboardMarkup
}

private fun initButton(pair: Pair<String, CallbackQueryDTO>): InlineKeyboardButton {
    val button = InlineKeyboardButton()
    button.text = pair.first
    button.callbackData = objectMapper.writeValueAsString(pair.second)
    return button
}

private fun insertButton(
    colNum: Int,
    button: InlineKeyboardButton,
    row: MutableList<InlineKeyboardButton>,
    keyboard: MutableList<MutableList<InlineKeyboardButton>>
) {
    when {
        row.size < colNum -> row.add(button)
        else -> {
            keyboard.add(row.toMutableList())
            row.clear()
            row.add(button)
        }
    }
}