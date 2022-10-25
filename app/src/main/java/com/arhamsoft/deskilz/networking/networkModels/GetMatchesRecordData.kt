package com.arhamsoft.deskilz.networking.networkModels

import java.io.Serializable

data class GetMatchesRecordData(
    val isWin: Boolean,
    val placePosition: String,
    val listofOpponent: List<ListofOpponentsMatchesRecord>,
    val time: String,
    val entryFee: String,
    val positionImage: String,
    val cash: Long,
    val xpValueUser: Long,
    val matchId: String,
    val matchTitle: String,
    val isTournament: Boolean
):Serializable