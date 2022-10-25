package com.arhamsoft.deskilz.networking.networkModels

data class PrizesModel(
    val rewardID: String,
    val rewardName: String,
    val rewardDescription: String,
    val rewardImage: String,
    val rewardType: String,
    val rewardPrice: Long
)
