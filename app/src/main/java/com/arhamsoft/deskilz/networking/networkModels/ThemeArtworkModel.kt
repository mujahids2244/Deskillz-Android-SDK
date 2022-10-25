package com.arhamsoft.deskilz.networking.networkModels

data class ThemeArtworkModel(
    val backgroundImage: String,
    val cashPrizeImage: String,
    val virtualPrizeImage: String,
    val virtualPrizeBackgroundColor: List<String>,
    val ticketsPrizeImage: String,
    val ticketsPrizeBackgroundColor: List<String>,
    val eventPrizeImage: String,
    val eventPrizeBackgroundColor: List<String>,
    val bracketedCashPrizeImage: String,
    val bracketCashPrizeBackgroundColor: List<String>,
    val bracketedVirtualPrizeImage: String,
    val bracketVirtualPrizeBackgroundColor: List<String>,
    val bracketedEventPrizeImage: String,
    val bracketEventPrizeBackgroundColor: List<String>
)
