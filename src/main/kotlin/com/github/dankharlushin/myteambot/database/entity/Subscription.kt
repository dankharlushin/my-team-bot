package com.github.dankharlushin.myteambot.database.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "subscription")
@SecondaryTable(name = "match", pkJoinColumns = [PrimaryKeyJoinColumn(name = "id")], foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
class Subscription( //FIXME double tables
    @ManyToOne
    var subscriber: Subscriber,
    @ManyToOne
    var match: Match,
    @Column(table = "match")
    var localTeamScore: Int? = null,
    @Column(table = "match")
    var visitorTeamScore: Int? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) : Serializable