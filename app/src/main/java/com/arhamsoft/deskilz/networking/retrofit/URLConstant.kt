package com.arhamsoft.deskilz.networking.retrofit

import com.arhamsoft.deskilz.networking.networkModels.CustomPlayerModel
import com.arhamsoft.deskilz.networking.networkModels.CustomPlayerModelData
import com.arhamsoft.deskilz.networking.networkModels.ThemeModel

object URLConstant {


    const val baseUrl = "https://deskillz.arhamsoft.org/v1/mobile/"
//    const val loginUrl = "login"
//    const val logoutUrl = "logout"
    const val acceptReqUrl = "users/accept-friend-request"
    const val addFriendUrl = "users/add-friend"
    const val setupNotifUrl = "sdk/setup-notification"
    const val coreloopUrl = "sdk/core-loop"
    const val signInUrl = "auth/signin"
    const val signUpUrl = "auth/signup"
    const val forgotPassUrl = "auth/forgot-password"
    const val updateProfileUrl = "users/update-profile"
    const val getAllUsers = "users/get-users"
    const val getRequest = "users/get-requests"
    const val sendMessage = "users/send-message"
    const val getCustomPlayerDataUrl = "games/get-custom-player-data"
    const val getGameCustomDataUrl = "games/get-game-custom-data"
    const val sendPlayerAccount = "games/send-player-account"
    const val getRandomPlayer = "games/get-random-player"
    const val getChats = "users/get-chats"
    const val getChatsHead = "users/get-chat-heads"
    const val getPlayerAccount = "games/get-player-account"
    const val getMatches = "games/get-tournaments"
    const val getRewards = "games/get-rewards"
    const val getProgressionData = "games/get-progression"
    const val getMarketLoadMore = "games/get-market-load-more"
    const val getMarket = "games/get-market"
    const val getEvents = "games/get-event"
    const val getAvailPromoCode = "games/avail-promo-code"
    const val getUserDetailedInfo = "games/user-detailed-info"
    const val getMatchesRecord = "games/get-match-record"
    const val updateMatchScore = "games/update-match-score"
    const val receiveNotification = "users/receive-notification-history"
    const val tokenExchange = "users/token-exchange-rate"
    const val playerWaitingListUrl = "users/player-waiting-list"
    const val playerRankings = "games/player-ranking"
    const val reportPlayer = "users/report-player"
    const val participate = "games/participate-in-tournament"
    const val logout = "auth/logout"
    const val changePassword = "auth/change-password"
    const val getTheme = "theme/get-theme-data"
    const val redeemPoints = "users/update-redeem-points"
    const val switchAcc = "auth/switch-account"
    const val checkUser = "users/check-friend"


    var long: Double = 0.0
    var lat: Double = 0.0
    var Fee:String =""

    lateinit var themeModel: ThemeModel
//    var userId:String?=""
    var fcmToken:String?=""
//    var check:Boolean = false
    var eventId: String? =""
    var matchId: String? = ""
    var playerId: String =""
    var userImg: String = ""
    var u_id:String?="0"
    var chatHeadId:String?=""
    var points:String?=""
    var player_count:Long? = 0L
    var joinType:Int = 9
    var currentLoginId:Int = 0
    var score:Long=85
    var gameActivity:String = ""


}