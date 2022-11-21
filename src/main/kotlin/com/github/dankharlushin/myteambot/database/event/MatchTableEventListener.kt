package com.github.dankharlushin.myteambot.database.event

import com.github.dankharlushin.myteambot.database.entity.Match
import com.github.dankharlushin.myteambot.database.repository.SubscriptionRepository
import org.hibernate.event.spi.PreInsertEvent
import org.hibernate.event.spi.PreInsertEventListener
import org.hibernate.event.spi.PreUpdateEvent
import org.hibernate.event.spi.PreUpdateEventListener
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.PreUpdate

@Component
class MatchTableEventListener(
    val applicationEventPublisher: ApplicationEventPublisher
) {

    companion object {
        private val LOG = LoggerFactory.getLogger(MatchTableEventListener::class.java)
    }

    @PreUpdate
    fun preUpdate(match: Match) {
        val previousVersion = match.previousVersion!!
        if (previousVersion.matchStatus != match.matchStatus)
            publishMatchStatusEvent(previousVersion, match)
        if (previousVersion.localTeamScore != match.localTeamScore || previousVersion.visitorTeamScore != match.visitorTeamScore)
            publishGoalEvent(previousVersion, match)
    }

    private fun publishMatchStatusEvent(previousVersion: Match, match: Match) {
        applicationEventPublisher.publishEvent(ChangeMatchStatusEvent(
            source = this,
            matchId = match.id,
            oldStatus = previousVersion.matchStatus,
            newStatus = match.matchStatus,
            homeTeam = match.homeTeam.name,
            visitorTeam = match.awayTeam.name,
            Pair(match.localTeamScore, match.visitorTeamScore))
        )

        LOG.info("MatchStatusEvent for match: '${match.id}' has been published")
    }

    private fun publishGoalEvent(previousVersion: Match, match: Match) {
        val goalStatus = initGoalStatus(previousVersion, match)
        val afterEventScore = Pair(match.localTeamScore, match.visitorTeamScore)

        val goalEvent = GoalEvent(
            source = this,
            matchId = match.id,
            homeTeam = match.homeTeam.name,
            visitorTeam = match.awayTeam.name,
            goalStatus = goalStatus,
            afterEventScore = afterEventScore)

        applicationEventPublisher.publishEvent(goalEvent)
        LOG.info("GoalEvent with status: '${goalEvent.goalStatus}' has been published")
    }

    private fun initGoalStatus(old: Match, new: Match): GoalStatus {
        return if (new.localTeamScore != old.localTeamScore) {
            if (new.localTeamScore > old.localTeamScore)
                GoalStatus.HOME_TEAM_GOAL
            else
                GoalStatus.HOME_TEAM_CANCELLATION
        } else {
            if (new.visitorTeamScore > old.visitorTeamScore)
                GoalStatus.VISITOR_TEAM_GOAL
            else
                GoalStatus.VISITOR_TEAM_CANCELLATION
        }
    }
}