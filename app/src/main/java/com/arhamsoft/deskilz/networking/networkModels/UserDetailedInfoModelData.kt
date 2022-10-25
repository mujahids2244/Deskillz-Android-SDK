package com.arhamsoft.deskilz.networking.networkModels

data class UserDetailedInfoModelData(
    val deskillzLevel: Long,
    val currentGameRank: Long,
    val currentGameImag: String,
    val progressXp: Long,
    val userWin: Long,
    val userLoose: Long,
    val winStreak: Long,
    val trophies: List<Trophy>,
    val userPlayedGamesInfo: List<UserPlayedGamesInfo>,
    val userData: UpdateProfileModelData
)
