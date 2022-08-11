package com.github.dankharlushin.myteambot.database.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    var firstName: String,
    @OneToMany(targetEntity = Match::class)
    var matches: List<Match>? = emptyList(),
    @OneToMany(targetEntity = Team::class)
    var teams: List<Team>? = emptyList(),
    var lastName: String? = null,
    var userName: String? = null,
    @Id
    var id: Int//TODO base entity?
)
