package com.arhamsoft.deskilz.networking.networkModels

data class GetTournamentsList(
    val headToHead: List<GetTournamentsListData>,
    val brackets: List<GetTournamentsListData>,
    val Practice: List<GetTournamentsListData>
)
