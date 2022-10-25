package com.arhamsoft.deskilz.networking.networkModels

data class UpdateMatchScoreModel(
    val status: Int,
    val message: String,
    val data: UpdateMatchScoreModelData
)
