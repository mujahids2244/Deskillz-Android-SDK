package com.arhamsoft.deskilz.networking.networkModels

data class PlayerWaitingModelData(
    val _id: String,

    val tournamentId: String,
    val tournamentName :String,
    val waitingFor: String,
    val entryFee: String,
    val winningPrize: Long,
    val previousScore: String,
    val playerCount: Long,
    val isPlayed: Boolean,
    val tournamentImage: String
)
