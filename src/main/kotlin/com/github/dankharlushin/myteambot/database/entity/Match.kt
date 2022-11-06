package com.github.dankharlushin.myteambot.database.entity

import com.github.dankharlushin.myteambot.database.event.MatchTableEventListener
import org.apache.commons.lang3.SerializationUtils
import java.io.Serializable
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "match")
@EntityListeners(MatchTableEventListener::class)
class Match(
    var matchStart: ZonedDateTime,
    @Enumerated(EnumType.STRING)
    var matchStatus: MatchStatus,
    var leagueId: Long,
    var seasonId: Long,
    var localTeamScore: Int,
    var visitorTeamScore: Int,
    @ManyToOne
    var homeTeam: Team,
    @ManyToOne
    var awayTeam: Team,
    @ManyToOne
    var venue: Venue?,
    @Id
    var id: Long,
    @Transient
    var previousVersion: Match? = null
) : Serializable {

    @PostLoad
    fun beforeAnyOperation() {
        this.previousVersion = SerializationUtils.clone(this)
    }
}