package com.github.dankharlushin.myteambot.database.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "country")
class Country(
    var name: String,
    var continent: String,
    @Id
    var id: Int
) : Serializable