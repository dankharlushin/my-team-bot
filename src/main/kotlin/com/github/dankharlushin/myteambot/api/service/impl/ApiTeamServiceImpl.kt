package com.github.dankharlushin.myteambot.api.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.model.ApiTeam
import com.github.dankharlushin.myteambot.api.service.ApiTeamService
import org.springframework.stereotype.Service

@Service
class ApiTeamServiceImpl(val restClient: RestClient): ApiTeamService {

    override fun getTeamsByCountryId(countryId: Int): List<ApiTeam> {
        return restClient.getListEntity("/soccer/teams?country_id=${countryId}", ApiTeam::class.java)
    }

    override fun getTeamById(teamId: Int): ApiTeam {
        return restClient.getEntity("/soccer/teams/${teamId}", ApiTeam::class.java)
    }

    override fun getTeamByName(name: String): ApiTeam {
        return TODO()
    }
}