package com.arhamsoft.deskilz.networking.networkModels

data class GetPlayerAccountData( //getcustomplayerdata and getgamecustomdata same model
    val userTickets: Int =0 ,
    val userDollers: Double =0.0,
    val userDeskillzCurrency: Int=0
    )
