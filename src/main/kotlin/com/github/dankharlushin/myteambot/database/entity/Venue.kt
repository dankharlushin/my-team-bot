package com.github.dankharlushin.myteambot.database.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "venue")
class Venue(
    var name: String,
    var capacity: Int?,
    var city: String,
    @Id
    var id: Long
)