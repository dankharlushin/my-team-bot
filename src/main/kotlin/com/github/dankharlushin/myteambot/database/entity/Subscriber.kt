package com.github.dankharlushin.myteambot.database.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "subscriber")
class Subscriber(
    @Id
    var id: Int,//TODO base entity?
    var name: String,
    var lastName: String? = null,
    var userName: String? = null,
    @OneToMany(targetEntity = Match::class)
    var matches: MutableSet<Match> = mutableSetOf(),
    @OneToMany(targetEntity = Team::class)
    var teams: MutableSet<Team> = mutableSetOf()
)
