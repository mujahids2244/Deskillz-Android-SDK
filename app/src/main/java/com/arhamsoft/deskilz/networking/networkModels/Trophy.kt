package com.arhamsoft.deskilz.networking.networkModels

data class Trophy (
    val trophyID: String,
    val trophyName: String,
    val trophyDescription: String,
    val trophyImage: String,
    val trophyType: Long,
    val rewardPrice: Double
)