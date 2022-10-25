package com.arhamsoft.deskilz.networking.networkModels

data class PlayerRankingModelData(
    val _id: String,

    val profileImage: String,
    val userName: String,
    val userEmail: String,

    val SDK_Rank: Long? = null,

    val countryFlag: String
)
