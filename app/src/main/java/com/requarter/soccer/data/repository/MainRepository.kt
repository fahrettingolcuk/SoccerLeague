package com.requarter.soccer.data.repository

import com.requarter.soccer.data.api.ApiHelper
import com.requarter.soccer.data.model.Team
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {
    fun getTeams(): Single<List<Team>> {
        return apiHelper.getTeams()
    }
}