package com.arhamsoft.deskilz.domain.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.networking.networkModels.*
import com.arhamsoft.deskilz.networking.retrofit.CallApi
import com.arhamsoft.deskilz.networking.retrofit.ResponseHandler
import com.arhamsoft.deskilz.networking.retrofit.RetrofitClient
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody

object NetworkRepo {

    private var retrofitClient = RetrofitClient.getInstance()
    private val callApi = CallApi.getInstance()
    var signupsuccessLiveData: MutableLiveData<SignUpModel> = MutableLiveData()
    var forgotsuccessLiveData: MutableLiveData<ForgotModel> = MutableLiveData()
//    var firebasesuccessLiveData: MutableLiveData<StatusModel> = MutableLiveData()
//    var logoutsuccessLiveData: MutableLiveData<StatusModel> = MutableLiveData()
    var loginsuccessLiveData: MutableLiveData<LoginModel> = MutableLiveData()
//    var generateSuccessLiveData: MutableLiveData<GenerateCodeModel> = MutableLiveData()
    var errorLiveData: MutableLiveData<ErrorModel> = MutableLiveData()






    suspend fun register(
        userName: String,
        userEmail: String,
        password: String,
        country: String,
        listener: NetworkListener<SignUpModel>

    ) {
        callApi.callApi(
            retrofitClient.register(userName,userEmail,password,country),
            object : ResponseHandler<SignUpModel> {
                override fun success(model: SignUpModel) {
                    model.let {
                        listener.successFul(it)
                    }

                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }




    suspend fun login(
        email: String,
        password: String,
        deviceId: String,
        fcmToken:String,
        listener: NetworkListener<LoginModel>

    ) {
        callApi.callApi(
            retrofitClient.login(email, password, deviceId,fcmToken),
            object : ResponseHandler<LoginModel> {
                override fun success(model: LoginModel) {
                    model.let {
                        listener.successFul(it)
                    }

                }

                override fun failure(error: Any) {
                   listener.failure()
                }
            }
        )
    }


    suspend fun switchAcc(
        userId: String,
        latsUserId: String,
        fcmToken: String,
        listener: NetworkListener<LoginModel>

    ) {
        callApi.callApi(
            retrofitClient.switchAcc(userId,latsUserId,fcmToken),
            object : ResponseHandler<LoginModel> {
                override fun success(model: LoginModel) {
                    model.let {
                        listener.successFul(it)
                    }

                }

                override fun failure(error: Any) {
                   listener.failure()
                }
            }
        )
    }


    suspend fun forgot(
        email: String,
        d_id:String,
        listener: NetworkListener<ForgotModel>

    ) {
        callApi.callApi(
            retrofitClient.forgotPass(email,d_id),
            object : ResponseHandler<ForgotModel> {
                override fun success(model: ForgotModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                 listener.failure()
                }
            }
        )
    }


    suspend fun coreLoop(
       gameID:String,
        listener: NetworkListener<ForgotModel>
    ) {
        callApi.callApi(
            retrofitClient.coreLoop(gameID),
            object : ResponseHandler<ForgotModel> {
                override fun success(model: ForgotModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }

                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun updateProfile(
        userID: String,
        userName:String,
        shoutout:String,
        img: MultipartBody.Part,
        listener: NetworkListener<UpdateProfileModel>
    ) {
        callApi.callApi(
            retrofitClient.updateProfile(
                userID.toRequestBody("text/plain".toMediaTypeOrNull()),
                userName.toRequestBody("text/plain".toMediaTypeOrNull()),
                shoutout.toRequestBody("text/plain".toMediaTypeOrNull()),
                img),
            object : ResponseHandler<UpdateProfileModel> {
                override fun success(model: UpdateProfileModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }

                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun getAllUsers(
        model: ChatPost,
        listener: NetworkListener<GetAllUsersModel>
    ) {
        callApi.callApi(
            retrofitClient.getAllUsers(model.limit,model.offset,model.userId),
            object : ResponseHandler<GetAllUsersModel> {
                override fun success(model: GetAllUsersModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }

                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun reportPlayer(
        userId: String,
        matchID: String,
        category:String,
        description:String,
        listener: NetworkListener<ForgotModel>
    ) {
        callApi.callApi(
            retrofitClient.report(userId,matchID,category,description),
            object : ResponseHandler<ForgotModel> {
                override fun success(model: ForgotModel) {
                    model.let {
                        listener.successFul(it)
                    }

                }

                override fun failure(error: Any) {
                  listener.failure()
                }
            }
        )
    }

    suspend fun participateTournament(
        userId: String,
        tournamentId: String,
        transactionHash:String,
        listener: NetworkListener<ForgotModel>
    ) {
        callApi.callApi(
            retrofitClient.participate(userId,tournamentId,transactionHash),
            object : ResponseHandler<ForgotModel> {
                override fun success(model: ForgotModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                  listener.failure()
                }
            }
        )
    }

    suspend fun getMatchesRecord(
        model: ChatPost,
        listener: NetworkListener<GetMatchesRecord>
    ) {
        callApi.callApi(
            retrofitClient.getMatchesRecord(model.limit,model.offset,model.userId),
            object : ResponseHandler<GetMatchesRecord> {
                override fun success(model: GetMatchesRecord) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun receiveNotifications(
        model: ChatPost,
        listener: NetworkListener<NotificationModel>
    ) {
        callApi.callApi(
            retrofitClient.receiveNotifications(model.limit,model.offset,model.userId),
            object : ResponseHandler<NotificationModel> {
                override fun success(model: NotificationModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun tokenExchange(
        listener: NetworkListener<TokenExchangeModel>
    ) {
        callApi.callApi(
            retrofitClient.tokenExchange(),
            object : ResponseHandler<TokenExchangeModel> {
                override fun success(model: TokenExchangeModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun sendMsgOne2one(
        userFromID: String,
        message:String,
        chatType: Int,
        userToID: String,
        listener: NetworkListener<SendMsgModel>
    ) {
        callApi.callApi(
            retrofitClient.sendMsgOne(userFromID,message,chatType,userToID),
            object : ResponseHandler<SendMsgModel> {
                override fun success(model: SendMsgModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }

                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun sendMsgGroup(
        userFromID: String,
        message:String,
        chatType: Int,
        listener: NetworkListener<SendMsgModel>
    ) {
        callApi.callApi(
            retrofitClient.sendMsgGroup(userFromID,message,chatType),
            object : ResponseHandler<SendMsgModel> {
                override fun success(model: SendMsgModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }

                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun getPlayerAccount(
        userId: String,
       listener: NetworkListener<GetPlayerAccount>
    ) {
        try {
            callApi.callApi(
                retrofitClient.getPlayerAccount(userId),
                object : ResponseHandler<GetPlayerAccount> {
                    override fun success(model: GetPlayerAccount) {
//                    forgotsuccessLiveData.postValue(model)
                        model.let {
                            listener.successFul(it)
                        }
                    }

                    override fun failure(error: Any) {

                        listener.failure()
                    }
                }
            )
        }
        catch (e:Exception){

            Log.e("exception", "APi:$e ")
            listener.failure()
        }
    }

    suspend fun addFriends(
        userId: String,
        playerId:String,
        listener: NetworkListener<AddFriendModel>
    ) {
        callApi.callApi(
            retrofitClient.addFriend(userId,playerId),
            object : ResponseHandler<AddFriendModel> {
                override fun success(model: AddFriendModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun checkFriend(
        userId: String,
        playerId:String,
        listener: NetworkListener<ForgotModel>
    ) {
        callApi.callApi(
            retrofitClient.checkFriend(userId,playerId),
            object : ResponseHandler<ForgotModel> {
                override fun success(model: ForgotModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun getRandomPlayer(
        userId: String,
        tournamentId:String,
        listener: NetworkListener<GetRandomPlayerModel>
    ) {
        callApi.callApi(
            retrofitClient.getRandomPlayer(userId,tournamentId),
            object : ResponseHandler<GetRandomPlayerModel> {
                override fun success(model: GetRandomPlayerModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun getChats(
        model:ChatPost,
        listener: NetworkListener<GetChatsModel>
    ) {
        callApi.callApi(
            retrofitClient.getChats(model.limit,model.offset),
            object : ResponseHandler<GetChatsModel> {
                override fun success(model: GetChatsModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun getFriendReq(
        model:ChatPost,
        listener: NetworkListener<GetFriendRequestListModel>
    ) {
        callApi.callApi(
            retrofitClient.getRequest(model.limit,model.offset,model.userId),
            object : ResponseHandler<GetFriendRequestListModel> {
                override fun success(model: GetFriendRequestListModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }
    suspend fun acceptFriendReq(
        userId: String,
        playerId: String,
        isAccepted: Boolean,
        listener: NetworkListener<AcceptFriendModel>
    ) {
        callApi.callApi(
            retrofitClient.acceptFriend(userId,playerId,isAccepted),
            object : ResponseHandler<AcceptFriendModel> {
                override fun success(model: AcceptFriendModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun redeemPoints(
        userId: String,
        loyaltyPoints: String,
        listener: NetworkListener<RedeemPointsModel>
    ) {
        callApi.callApi(
            retrofitClient.redeemPoints(userId, loyaltyPoints),
            object : ResponseHandler<RedeemPointsModel> {
                override fun success(model: RedeemPointsModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun getChatsHeads(
        model:ChatPost,
        listener: NetworkListener<GetChatsHeadModel>
    ) {
        callApi.callApi(
            retrofitClient.getChatsHead(model.limit,model.offset,model.userId),
            object : ResponseHandler<GetChatsHeadModel> {
                override fun success(model: GetChatsHeadModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun getPlayerRanking(
        model:ChatPost,
        listener: NetworkListener<PlayerRankingModel>
    ) {
        callApi.callApi(
            retrofitClient.getPlayerRanking(model.limit,model.offset,model.userId),
            object : ResponseHandler<PlayerRankingModel> {
                override fun success(model: PlayerRankingModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun getMatches(
        listener: NetworkListener<GetTournaments>
    ) {
        callApi.callApi(
            retrofitClient.getMatches(),
            object : ResponseHandler<GetTournaments> {
                override fun success(model: GetTournaments) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }
    suspend fun getTheme(
        listener: NetworkListener<ThemeModel>
    ) {
        callApi.callApi(
            retrofitClient.getTheme(),
            object : ResponseHandler<ThemeModel> {
                override fun success(model: ThemeModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }
    suspend fun getRewards(
        userId: String,
        listener: NetworkListener<GetRewards>
    ) {
        callApi.callApi(
            retrofitClient.getRewards(userId),
            object : ResponseHandler<GetRewards> {
                override fun success(model: GetRewards) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun updateScore(
        score: Long,
        userId: String,
        matchID: String,
        listener: NetworkListener<UpdateMatchScoreModel>
    ) {
        callApi.callApi(
            retrofitClient.updateMatchScore(score, matchID, userId),
            object : ResponseHandler<UpdateMatchScoreModel> {
                override fun success(model: UpdateMatchScoreModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun getAvailPromoCode(
        userId: String,
        promoCode:String,
        listener: NetworkListener<GetRewards>
    ) {
        callApi.callApi(
            retrofitClient.getAvailPromoCode(userId,promoCode),
            object : ResponseHandler<GetRewards> {
                override fun success(model: GetRewards) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    suspend fun getUserDetailedInfo(
        userId: String,
        listener: NetworkListener<UserDetailedInfoModel>
    ) {
        callApi.callApi(
            retrofitClient.getuserDetailedInfo(userId),
            object : ResponseHandler<UserDetailedInfoModel> {
                override fun success(model: UserDetailedInfoModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

//    suspend fun getPlayerWaitingList(
//        userId: String,
//        listener: NetworkListener<NotificationModel>
//    ) {
//        callApi.callApi(
//            retrofitClient.getPlayerWaitingList(userId),
//            object : ResponseHandler<NotificationModel> {
//                override fun success(model: NotificationModel) {
//                    model.let {
//                        listener.successFul(it)
//                    }
//                }
//
//                override fun failure(error: Any) {
//                    listener.failure()
//                }
//            }
//        )
//    }

    suspend fun getMarketLoadMore(
        model: MarketLoadMorePost,
        listener: NetworkListener<GetMarketLoadMoreModel>
    ) {
        callApi.callApi(
            retrofitClient.getMarketLoadMore(model.marketId,model.limit,model.offset),
            object : ResponseHandler<GetMarketLoadMoreModel> {
                override fun success(model: GetMarketLoadMoreModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

 suspend fun getMarket(
        limit:Int,
        listener: NetworkListener<GetMarketLoadMoreModel>
    ) {
        callApi.callApi(
            retrofitClient.getmarket(limit),
            object : ResponseHandler<GetMarketLoadMoreModel> {
                override fun success(model: GetMarketLoadMoreModel) {
//                    forgotsuccessLiveData.postValue(model)
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun playerWaiting(
        userId: String,
        listener: NetworkListener<PlayerWaitingModel>
    ) {
        callApi.callApi(
            retrofitClient.playerWaitingList(userId),
            object : ResponseHandler<PlayerWaitingModel> {
                override fun success(model: PlayerWaitingModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun getEvent(
        listener: NetworkListener<EventsModel>
    ) {
        callApi.callApi(
            retrofitClient.getEvents(URLConstant.long,URLConstant.lat),
            object : ResponseHandler<EventsModel> {
                override fun success(model: EventsModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }


    suspend fun getGameCustomData(
        listener: NetworkListener<CustomPlayerModel>
    ) {
        callApi.callApi(
            retrofitClient.getGameCustomData(),
            object : ResponseHandler<CustomPlayerModel> {
                override fun success(model: CustomPlayerModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

//    suspend fun getProgressionData(
//        keyCondition: String,
//        keyValue:Long,
//        listener: NetworkListener<GetMarketLoadMoreModel>
//    ) {
//
//        val checked = ProgressionPost(
//            listOf(Condition(keyCondition,keyValue)) as ArrayList<Condition>
//        )
//        val gson: JsonObject = JsonParser.parseString(Gson().toJson(checked)).asJsonObject
//
//        callApi.callApi(
//            retrofitClient.getProgressionData(gson),
//            object : ResponseHandler<GetMarketLoadMoreModel> {
//                override fun success(model: GetMarketLoadMoreModel) {
////                    forgotsuccessLiveData.postValue(model)
//                    model.let {
//                        listener.successFul(it)
//                    }
//                }
//
//                override fun failure(error: Any) {
//                    listener.failure()
//                }
//            }
//        )
//    }

    suspend fun getProgressionData(
        conditionValue: ArrayList<ProgressPost>,
        listener: NetworkListener<ProgressionModel>
    ) {

        val checked = ProgressionPost(conditionValue)

        val gson: JsonObject = JsonParser.parseString(Gson().toJson(checked)).asJsonObject

        callApi.callApi(
            retrofitClient.getProgressionData(gson),
            object : ResponseHandler<ProgressionModel> {
                override fun success(model: ProgressionModel) {
                    model.let {
                        listener.successFul(it)
                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }
            }
        )
    }

    fun updateRetrofitClientInstance() {
        retrofitClient = RetrofitClient.updateInstance()
    }

    suspend fun logout(
        userId: String,
        listener: NetworkListener<ForgotModel>
    ) {
        callApi.callApi(
            retrofitClient.logoutUser(userId),
            object : ResponseHandler<ForgotModel> {
                override fun success(model: ForgotModel) {
                    model.let {
                        listener.successFul(it)

                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }

            })
    }
    suspend fun changePassword(
        userId: String,
        oldPassword:String,
        newPassword:String,
        listener: NetworkListener<ForgotModel>
    ) {
        callApi.callApi(
            retrofitClient.changePassword(userId,oldPassword,newPassword),
            object : ResponseHandler<ForgotModel> {
                override fun success(model: ForgotModel) {
                    model.let {
                        listener.successFul(it)

                    }
                }

                override fun failure(error: Any) {
                    listener.failure()
                }

            })
    }

}