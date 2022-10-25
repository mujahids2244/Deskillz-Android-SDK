package com.arhamsoft.deskilz.networking.networkModels

data class UpdateMatchScoreModelData(
    val matchID: String,
    val tournamentName: String,
    val gameImage: String,
    val gameGenre: String,
    val gameInstructions: String,
    val gamePlatform: Long,
    val matchScore: String,
    val opponentId: String
)
