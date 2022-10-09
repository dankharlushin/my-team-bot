package com.github.dankharlushin.myteambot.api.sportdata.service.impl

import com.github.dankharlushin.myteambot.api.client.RestClient
import com.github.dankharlushin.myteambot.api.sportdata.model.SdApiTeam
import com.github.dankharlushin.myteambot.api.sportdata.service.SdApiTeamService
import org.springframework.stereotype.Service

@Deprecated("bug in sportdata api")
@Service
class SdApiTeamServiceImpl(val restClient: RestClient): SdApiTeamService {

    override fun getTeamsByCountryId(countryId: Int): List<SdApiTeam> {
        return restClient.getListEntity("/soccer/teams?country_id=${countryId}", SdApiTeam::class.java)
    }

    override fun getTeamById(teamId: Int): SdApiTeam {
        return restClient.getEntity("/soccer/teams/${teamId}", SdApiTeam::class.java)
    }
}