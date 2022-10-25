package com.arhamsoft.deskilz.networking.networkModels

data class GetMatchesRecord(
    val status: Int,
    val message: String,
    val data: List<GetMatchesRecordData>
)
