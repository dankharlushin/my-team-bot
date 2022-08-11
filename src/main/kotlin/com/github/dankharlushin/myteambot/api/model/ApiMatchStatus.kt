package com.github.dankharlushin.myteambot.api.model

import com.fasterxml.jackson.annotation.JsonValue

enum class ApiMatchStatus(@JsonValue val code: Int) {
    NOT_STARTED(0),
    INPLAY(1),
    UPDATE_LATER(2),
    ENDED(3),
    POSTPONED(4),
    CANCELLED(5),
    ABANDONED(6),
    INTERRUPTED(7),
    SUSPENDED(8),
    AWARDED(9),
    DELAYED(10),
    HALF_TIME(11),
    EXTRA_TIME(12),
    PENALTIES(13),
    BREAK_TIME(14),
    AWARDING(15),
    TO_BE_ANNOUNCED(17),
    AFTER_PENALTIES(31),
    AFTER_EXTRA_TIME(32)
}