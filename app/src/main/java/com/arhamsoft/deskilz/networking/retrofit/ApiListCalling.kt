package com.arhamsoft.deskilz.networking.retrofit

import android.graphics.Bitmap
import com.arhamsoft.deskilz.networking.networkModels.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import org.bytedeco.javacpp.annotation.Raw

interface ApiListCalling {

    @FormUrlEncoded
    @POST(URLConstant.signInUrl)
    suspend fun login(
        @Field("userEmail") userEmail:String,
        @Field("userPass") userPass:String,
        @Field("deviceId") deviceId:String,
        @Field("fcmToken") fcmToken:String,
    ): Response<LoginModel>

    @FormUrlEncoded
    @POST(URLConstant.switchAcc)
    suspend fun switchAcc(
        @Field("userId") userEmail:String,
        @Field("lastUserId") userPass:String,
        @Field("fcmToken") fcmToken:String,
    ): Response<LoginModel>

    @FormUrlEncoded
    @POST(URLConstant.forgotPassUrl)
    suspend fun forgotPass(
        @Field("userEmail") userEmail:String,
        @Field("deviceID") deviceId:String,
    ): Response<ForgotModel>

    @FormUrlEncoded
    @POST(URLConstant.setupNotifUrl)
    suspend fun setupNotif(
        @Field("gameID") gameID:String,
        @Field("setUpFlag") setUpFlag:Boolean,
    ): Response<ForgotModel>

    @FormUrlEncoded
    @POST(URLConstant.coreloopUrl)
    suspend fun coreLoop(
        @Field("gameID") matchID:String,
    ): Response<ForgotModel>

    @FormUrlEncoded
    @POST(URLConstant.addFriendUrl)
    suspend fun addFriend(
        @Field("userId") userId:String,
        @Field("playerId") playerId:String,
    ): Response<AddFriendModel>

    @FormUrlEncoded
    @POST(URLConstant.checkUser)
    suspend fun checkFriend(
        @Field("userId") userId:String,
        @Field("playerId") playerId:String,
    ): Response<ForgotModel>

    @FormUrlEncoded
    @POST(URLConstant.acceptReqUrl)
    suspend fun acceptFriend(
        @Field("userId") userId:String,
        @Field("playerId") playerId:String,
        @Field("isAccepted") isAccepted:Boolean,
    ): Response<AcceptFriendModel>

    @FormUrlEncoded
    @POST(URLConstant.redeemPoints)
    suspend fun redeemPoints(
        @Field("userId") userID:String,
        @Field("loyaltyPoints") loyaltyPoints:String,
    ): Response<RedeemPointsModel>

    @FormUrlEncoded
    @POST(URLConstant.getRequest)
    suspend fun getRequest(
        @Field("limit") limit:Int,
        @Field("offSet") offSet:Int,
        @Field("userId") userId:String,
    ): Response<GetFriendRequestListModel>

    @FormUrlEncoded
    @POST(URLConstant.getAllUsers)
    suspend fun getAllUsers(
        @Field("limit") limit:Int,
        @Field("offSet") offSet:Int,
        @Field("userId") userId:String,
    ): Response<GetAllUsersModel>

    @FormUrlEncoded
    @POST(URLConstant.getChats)
    suspend fun getChats(
        @Field("limit") limit:Int,
        @Field("offSet") offSet:Int,
    ): Response<GetChatsModel>

    @FormUrlEncoded
    @POST(URLConstant.getChatsHead)
    suspend fun getChatsHead(
        @Field("limit") limit:Int,
        @Field("offSet") offSet:Int,
        @Field("userId") userId:String,
    ): Response<GetChatsHeadModel>

    @FormUrlEncoded
    @POST(URLConstant.sendMessage)
    suspend fun sendMsgOne(
        @Field("userFromID") userFromID:String,
        @Field("message") message:String,
        @Field("chatType") chatType:Int,
        @Field("userToID") userToID:String,
    ): Response<SendMsgModel>

    @FormUrlEncoded
    @POST(URLConstant.sendMessage)
    suspend fun sendMsgGroup(
        @Field("userFromID") userFromID:String,
        @Field("message") message:String,
        @Field("chatType") chatType:Int,
    ): Response<SendMsgModel>

    @FormUrlEncoded
    @POST(URLConstant.getCustomPlayerDataUrl)
    suspend fun getCustomPlayerData(
        @Field("userFromID") userFromID:String,
        @Field("message") message:String,
        @Field("chatType") chatType:Int,
        @Field("userToID") userToID:String,
    ): Response<CustomPlayerModel>

//    @FormUrlEncoded                        //dont know response bcz response is empty in the doc
//    @POST(URLConstant.sendPlayerAccount)
//    suspend fun sendPlayerAccount(
//        @Field("userId") userId:String,
//        @Field("tournamentId") tournamentId:String,
//        @Field("keyValue") keyValue:String,
//    ): Response<>

    @FormUrlEncoded
    @POST(URLConstant.getRandomPlayer)
    suspend fun getRandomPlayer(
        @Field("userId") userId:String,
        @Field("tournamentId") tournamentId:String,
    ): Response<GetRandomPlayerModel>

    @FormUrlEncoded
    @POST(URLConstant.getMatchesRecord)
    suspend fun getMatchesRecord(
        @Field("limit") limit:Int,
        @Field("offSet") offSet:Int,
        @Field("userId") userId:String,
    ): Response<GetMatchesRecord>

    @FormUrlEncoded
    @POST(URLConstant.playerRankings)
    suspend fun getPlayerRanking(
        @Field("limit") limit:Int,
        @Field("offSet") offSet:Int,
        @Field("userId") userId:String,
    ): Response<PlayerRankingModel>

    @FormUrlEncoded
    @POST(URLConstant.receiveNotification)
    suspend fun receiveNotifications(
        @Field("limit") limit:Int,
        @Field("offSet") offSet:Int,
        @Field("userId") userId:String,
    ): Response<NotificationModel>

    @FormUrlEncoded
    @POST(URLConstant.tokenExchange)
    suspend fun tokenExchange(
    ): Response<TokenExchangeModel>

    @FormUrlEncoded
    @POST(URLConstant.playerWaitingListUrl)
    suspend fun playerWaitingList(
        @Field("userId") userId:String,
    ): Response<PlayerWaitingModel>


    @FormUrlEncoded
    @POST(URLConstant.getPlayerAccount)
    suspend fun getPlayerAccount(
        @Field("userId") userId:String,
    ): Response<GetPlayerAccount>


    @GET(URLConstant.getGameCustomDataUrl)
    suspend fun getGameCustomData(
    ): Response<CustomPlayerModel>

    @Multipart
    @POST(URLConstant.updateProfileUrl)
    suspend fun updateProfile(
        @Part("userId") userId: RequestBody,
        @Part("userName") userName:RequestBody,
        @Part("userShoutOut") userShoutOut:RequestBody,
        @Part img: MultipartBody.Part,
    ): Response<UpdateProfileModel>

    @FormUrlEncoded
    @POST(URLConstant.signUpUrl)
    suspend fun register(
        @Field("userName") userName :String,
        @Field("userEmail") userEmail :String,
        @Field("userPass") userPass :String,
        @Field("userCountry") userCountry :String,
    ): Response<SignUpModel>

    @FormUrlEncoded
    @POST(URLConstant.reportPlayer)
    suspend fun report(
        @Field("userId") userID :String,
        @Field("matchId") matchID :String,
        @Field("category") category :String,
        @Field("description") description :String,
    ): Response<ForgotModel>

    @FormUrlEncoded
    @POST(URLConstant.participate)
    suspend fun participate(
        @Field("userId") userId :String,
        @Field("tournamentId") tournamentId :String,
        @Field("transactionHash") transactionHash :String,
    ): Response<ForgotModel>

    @FormUrlEncoded
    @POST(URLConstant.changePassword)
    suspend fun changePassword(
        @Field("userId") userID :String,
        @Field("oldPassword") oldPassword :String,
        @Field("newPassword") newPassword :String,
    ): Response<ForgotModel>

    @FormUrlEncoded
    @POST(URLConstant.logout)
    suspend fun logoutUser(
        @Field("userId") userId :String,
    ): Response<ForgotModel>

    @FormUrlEncoded
    @POST(URLConstant.updateMatchScore)
    suspend fun updateMatchScore(
        @Field("matchScore") score:Long,
        @Field("matchId") matchID: String,
        @Field("userId") userId: String,
    ):Response<UpdateMatchScoreModel>



    @POST(URLConstant.getMatches)
    suspend fun getMatches(): Response<GetTournaments>


    @POST(URLConstant.getTheme)
    suspend fun getTheme(): Response<ThemeModel>

    @FormUrlEncoded
    @POST(URLConstant.getRewards)
    suspend fun getRewards(
        @Field("userId") userId:String,
    ): Response<GetRewards>

    @FormUrlEncoded
    @POST(URLConstant.getAvailPromoCode)
    suspend fun getAvailPromoCode(
        @Field("userId") userId:String,
        @Field("promoCode") promoCode:String,
    ): Response<GetRewards>

    @FormUrlEncoded
    @POST(URLConstant.getUserDetailedInfo)
    suspend fun getuserDetailedInfo(
        @Field("userId") userId:String,
    ): Response<UserDetailedInfoModel>

    @FormUrlEncoded
    @POST(URLConstant.getMarketLoadMore)
    suspend fun getMarketLoadMore(
        @Field("marketID") marketId:String,
        @Field("limit") limit:Int,
        @Field("offSet") offSet:Int,
    ): Response<GetMarketLoadMoreModel>

    @FormUrlEncoded
    @POST(URLConstant.getMarket)
    suspend fun getmarket(
        @Field("limit") limit:Int,
    ): Response<GetMarketLoadMoreModel>


    @GET(URLConstant.getEvents)
    suspend fun getEvents(@Query("longitude") long: Double, @Query("latitude") lat: Double): Response<EventsModel>

    @Raw
    @POST(URLConstant.getProgressionData)
    suspend fun getProgressionData(
        @Body data:JsonObject
    ): Response<ProgressionModel>

}