package com.github.dankharlushin.myteambot.database.service.impl

import com.github.dankharlushin.myteambot.database.entity.Match
import com.github.dankharlushin.myteambot.database.entity.Subscriber
import com.github.dankharlushin.myteambot.database.entity.Team
import com.github.dankharlushin.myteambot.database.repository.MatchRepository
import com.github.dankharlushin.myteambot.database.repository.SubscriberRepository
import com.github.dankharlushin.myteambot.database.service.SubscriptionService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.User

@Service
class SubscriptionServiceImpl(
    val subscriberRepository: SubscriberRepository,
    val matchRepository: MatchRepository
) : SubscriptionService {

    override fun subscribe(user: User, match: Match) {
        val subscriber = saveSubscriber(user)
        subscriber.matches.add(match)
        subscriberRepository.save(subscriber)
    }

    override fun subscribe(user: User, team: Team) {
        val subscriber = saveSubscriber(user)
        subscriber.teams.add(team)

        matchRepository.getTeamMatches(team.id).forEach{ subscribe(user, it) }
        subscriberRepository.save(subscriber)
    }

    override fun unsubscribe(user: User, match: Match) {
        val subscriber = subscriberRepository.getById(user.id.toInt())
        subscriber.matches.remove(match)
        subscriberRepository.save(subscriber)
    }

    override fun unsubscribe(user: User, team: Team) {
        val subscriber = subscriberRepository.getById(user.id.toInt())
        subscriber.teams.remove(team)

        matchRepository.getTeamMatches(team.id).forEach{ unsubscribe(user, it) }
        subscriberRepository.save(subscriber)
    }

    override fun isSubscribed(user: User, match: Match): Boolean {
        val dbUser = subscriberRepository.findById(user.id.toInt())
        if (dbUser.isPresent)
            return dbUser.get().matches.contains(match)

        return false
    }

    override fun isSubscribed(user: User, team: Team): Boolean {
        val dbUser = subscriberRepository.findById(user.id.toInt())
        if (dbUser.isPresent)
            return dbUser.get().teams.contains(team)

        return false
    }

    private fun saveSubscriber(user: User): Subscriber {
        val dbSubscriber = subscriberRepository.findById(user.id.toInt())
        if (dbSubscriber.isPresent)
            return dbSubscriber.get()

        val subscriber =
            Subscriber(id = user.id.toInt(), name = user.firstName, lastName = user.lastName, userName = user.userName)
        return subscriberRepository.save(subscriber)
    }

}