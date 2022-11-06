package com.github.dankharlushin.myteambot.database.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "subscriber")
class Subscriber(
    @Id
    var id: Int,//TODO base entity?
    var name: String,
    var chatId: String,
    var lastName: String? = null,
    var userName: String? = null,
    @ManyToMany(targetEntity = Match::class)
    var matches: MutableSet<Match> = mutableSetOf(),
    @ManyToMany(targetEntity = Team::class)
    var teams: MutableSet<Team> = mutableSetOf()
) : Serializable
