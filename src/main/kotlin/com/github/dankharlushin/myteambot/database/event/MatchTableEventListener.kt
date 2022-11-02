package com.github.dankharlushin.myteambot.database.event

import com.github.dankharlushin.myteambot.database.entity.Match
import org.hibernate.event.spi.PreInsertEvent
import org.hibernate.event.spi.PreInsertEventListener
import org.hibernate.event.spi.PreUpdateEvent
import org.hibernate.event.spi.PreUpdateEventListener
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import javax.persistence.PreUpdate

@Component
class MatchTableEventListener(val applicationEventPublisher: ApplicationEventPublisher) : PreInsertEventListener, PreUpdateEventListener {
//    val applicationEventPublisher: ApplicationEventPublisher,
//    val entityManagerFactory: EntityManagerFactory,
//    @PersistenceContext
//    val entityManager: EntityManager,
//    val matchRepository: MatchRepository
//) : PreInsertEventListener, PreUpdateEventListener {

    override fun onPreInsert(event: PreInsertEvent): Boolean {
        if (event.entity is Match) {

        }
        return false
    }


    override fun onPreUpdate(event: PreUpdateEvent): Boolean {
        val new = event.entity
        val old = (event.entity as Match).savedEntity
        return false
    }


    @PreUpdate
    fun preUpdate(match: Match) {

    }

//    @PostConstruct
//    private fun init() {
//        val sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl::class.java)
//        val registry = sessionFactory.serviceRegistry.getService(
//            EventListenerRegistry::class.java
//        )
//        registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(this)
//    }
}