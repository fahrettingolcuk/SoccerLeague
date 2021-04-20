package com.requarter.soccer.data.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
)
