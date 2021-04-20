package com.requarter.soccer.data.api

import com.requarter.soccer.data.model.Team
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImpl: ApiService {
    override fun getTeams(): Single<List<Team>> {
        return Rx2AndroidNetworking.get("https://6079d315460a6600174fc5cf.mockapi.io/api/teams/team")
            .build()
            .getObjectListSingle(Team::class.java)
    }
}