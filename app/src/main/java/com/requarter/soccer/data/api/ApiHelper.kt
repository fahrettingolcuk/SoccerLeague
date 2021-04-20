package com.requarter.soccer.data.api

class ApiHelper(private val apiService: ApiService) {
    fun getTeams() = apiService.getTeams()
}