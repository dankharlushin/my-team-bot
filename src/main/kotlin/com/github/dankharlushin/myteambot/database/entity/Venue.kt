package com.github.dankharlushin.myteambot.database.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "venue")
class Venue(
    var name: String,
    var capacity: Int,
    var city: String,
    @ManyToOne
    var country: Country?,
    @Id
    var id: Int
)