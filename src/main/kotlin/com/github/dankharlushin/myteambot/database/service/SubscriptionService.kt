package com.github.dankharlushin.myteambot.database.service

import com.github.dankharlushin.myteambot.database.entity.Match
import com.github.dankharlushin.myteambot.database.entity.Team
import org.telegram.telegrambots.meta.api.objects.User

interface SubscriptionService {

    fun subscribe(user: User, match: Match)

    fun subscribe(user: User, team: Team)

    fun unsubscribe(user: User, match: Match)

    fun unsubscribe(user: User, team: Team)

    fun isSubscribed(user: User, match: Match): Boolean

    fun isSubscribed(user: User, team: Team): Boolean
}