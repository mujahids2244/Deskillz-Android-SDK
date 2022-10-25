package com.arhamsoft.deskilz.networking.networkModels

data class UserPlayedGamesInfo(
    val gameID: String,
    val gameName: String,
    val gameImage: String,
    val gameGenre: String,
    val gameInstructions: String,
    val gamePlatform: Long,
    val gameRank: GameRank
)
