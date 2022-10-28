package com.arhamsoft.deskilz.networking.networkModels

data class GetRandomPlayerModelData(
    val matchID: String,
    val gameImage: String,
    val gameGenre: String,
    val gameInstructions: String,
    val gamePlatform: Long,

    val IsPlayable: Boolean,
    val playerCount: Int,

    val listOfOpponents: List<ListofOpponentModel>
)
