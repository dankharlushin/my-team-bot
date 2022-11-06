package com.github.dankharlushin.myteambot.database.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "team")
class Team(
    var name: String,
    var logo: String,
    @Id
    var id: Long
) : Serializable