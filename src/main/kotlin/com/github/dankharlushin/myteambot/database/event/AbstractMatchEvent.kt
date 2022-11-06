package com.github.dankharlushin.myteambot.database.event

import org.springframework.context.ApplicationEvent

abstract class AbstractMatchEvent(
    source: Any
) : ApplicationEvent(source)