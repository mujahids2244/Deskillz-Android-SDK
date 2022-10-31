package com.arhamsoft.deskilz.networking.networkModels


data class GetTournamentsListData(
    val isPractice: Boolean? = null,
    val gamePlay: Int?,
    val tournamentID: String?,
    val tournamentName: String?,
    val winningPrize: String?,
    val tournamentImage: String?,
    val entryFee: String?="N/A",
    val playerCount: Long?= 0,
    val currencyLabel: String?,
    val currencyType: String?,
    val gamePromoCode: String?,
    val prizes: List<PrizesModel?>

)
