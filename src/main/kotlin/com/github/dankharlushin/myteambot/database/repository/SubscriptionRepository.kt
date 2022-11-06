package com.github.dankharlushin.myteambot.database.repository

import com.github.dankharlushin.myteambot.database.entity.Subscription
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionRepository: JpaRepository<Subscription, Long> {

    fun getByMatchId(matchId: Long): Set<Subscription>?

    fun getBySubscriberIdAndMatchId(subscriberId: Int, matchId: Long): Subscription?

    fun removeBySubscriberIdAndMatchId(subscriberId: Int, matchId: Long)
}