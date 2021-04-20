package com.requarter.soccer.data.api

import com.requarter.soccer.data.model.Team
import io.reactivex.Single

interface ApiService {
    fun getTeams(): Single<List<Team>>
}