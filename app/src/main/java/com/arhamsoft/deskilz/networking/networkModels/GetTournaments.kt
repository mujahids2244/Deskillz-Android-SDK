package com.arhamsoft.deskilz.networking.networkModels

data class GetTournaments(
    val status: Int,
    val message: String,
    val data: GetTournamentsList
)
