package com.github.dankharlushin.myteambot.database.event

import com.github.dankharlushin.myteambot.database.entity.MatchStatus
import com.github.dankharlushin.myteambot.database.repository.SubscriptionRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class DatabaseEventMapper(
    val subscriptionRepository: SubscriptionRepository,
    val applicationEventPublisher: ApplicationEventPublisher,
    val sourceAccessor: MessageSourceAccessor
) {

    companion object {
        private const val GOAL_EVENT_CODE = "goalEvent"
        private const val GOAL_CANCELLATION_EVENT_CODE = "goalCancellationEvent"
        private const val MATCH_START_EVENT_CODE = "matchStartEvent"
        private const val MATCH_END_EVENT_CODE = "matchEndEvent"
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun mapGoalEvent(goalEvent: GoalEvent) {
        val score: String = when (goalEvent.goalStatus) {
            GoalStatus.HOME_TEAM_GOAL -> sourceAccessor.getMessage(
                GOAL_EVENT_CODE, arrayOf(
                    goalEvent.homeTeam,
                    goalEvent.visitorTeam, "[${goalEvent.afterEventScore.first}]", goalEvent.afterEventScore.second
                )
            )

            GoalStatus.VISITOR_TEAM_GOAL -> sourceAccessor.getMessage(
                GOAL_EVENT_CODE, arrayOf(
                    goalEvent.homeTeam,
                    goalEvent.visitorTeam, goalEvent.afterEventScore.first, "[${goalEvent.afterEventScore.second}]"
                )
            )

            GoalStatus.HOME_TEAM_CANCELLATION -> sourceAccessor.getMessage(
                GOAL_CANCELLATION_EVENT_CODE,
                arrayOf(
                    goalEvent.homeTeam,
                    goalEvent.visitorTeam,
                    "[${goalEvent.afterEventScore.first}]",
                    goalEvent.afterEventScore.second
                )
            )

            GoalStatus.VISITOR_TEAM_CANCELLATION -> sourceAccessor.getMessage(
                GOAL_CANCELLATION_EVENT_CODE,
                arrayOf(
                    goalEvent.homeTeam,
                    goalEvent.visitorTeam,
                    goalEvent.afterEventScore.first,
                    "[${goalEvent.afterEventScore.second}]"
                )
            )
        }

        subscriptionRepository.getByMatchId(goalEvent.matchId)?.forEach {
            applicationEventPublisher.publishEvent(
                SendMessageEvent(
                    source = this,
                    message = score,
                    chatId = it.subscriber.chatId
                )
            )
        }
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun mapChangeStatusEvent(changeStatusEvent: ChangeMatchStatusEvent) {
        var eventStatus: String? = null
        val newStatus = changeStatusEvent.newStatus
        val oldStatus = changeStatusEvent.oldStatus

        if (newStatus == MatchStatus.LIVE && oldStatus == MatchStatus.NS)
            eventStatus = sourceAccessor.getMessage(
                MATCH_START_EVENT_CODE,
                arrayOf(changeStatusEvent.homeTeam, changeStatusEvent.visitorTeam)
            )
        //FIXME can be break for extra-time matches
        else if (newStatus == MatchStatus.FT || newStatus == MatchStatus.AET || newStatus == MatchStatus.FT_PEN)
            eventStatus = sourceAccessor.getMessage(
                    MATCH_END_EVENT_CODE,
                arrayOf(changeStatusEvent.homeTeam, changeStatusEvent.visitorTeam,
                changeStatusEvent.afterEventScore.first, changeStatusEvent.afterEventScore.second))


        eventStatus?.let {
            //TODO add method for publication
            subscriptionRepository.getByMatchId(changeStatusEvent.matchId)?.forEach {
                applicationEventPublisher.publishEvent(
                    SendMessageEvent(
                        source = this,
                        message = eventStatus,
                        chatId = it.subscriber.chatId
                    )
                )
            }
        }
    }
}