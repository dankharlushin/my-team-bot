package com.github.dankharlushin.myteambot.database.repository

import com.github.dankharlushin.myteambot.database.entity.Subscriber
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriberRepository : JpaRepository<Subscriber, Int>