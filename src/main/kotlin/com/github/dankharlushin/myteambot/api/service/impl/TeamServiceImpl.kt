package com.github.dankharlushin.myteambot.api.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.model.Team
import com.github.dankharlushin.myteambot.api.service.TeamService
import org.springframework.stereotype.Service

@Service
class TeamServiceImpl(val restClient: RestClient): TeamService {

    override fun getTeamsByCountryId(countryId: Int): List<Team> {
        return restClient.getListEntity("/soccer/teams?country_id=${countryId}", Team::class.java)
    }

    override fun getTeamById(teamId: Int): Team {
        return restClient.getEntity("/soccer/teams/${teamId}", Team::class.java)
    }

    override fun getTeamByName(name: String): Team {
        return TODO()
    }
}