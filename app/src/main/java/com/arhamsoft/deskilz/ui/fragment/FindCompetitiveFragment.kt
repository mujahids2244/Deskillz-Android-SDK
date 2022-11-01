package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.DialogDepositBinding
import com.arhamsoft.deskilz.databinding.FragmentFindCompetitiveBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.*
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.services.SocketHandler
import com.arhamsoft.deskilz.ui.adapter.AdapterOpponents
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class FindCompetitiveFragment : Fragment() {

    private lateinit var binding: FragmentFindCompetitiveBinding
    lateinit var loading: LoadingDialog
    var u_id: String? = null
    var opponentList: ArrayList<ListofOpponentModel> = ArrayList()
    var t_id: String? = " "
    private lateinit var rvAdapter: AdapterOpponents
    lateinit var time: CountDownTimer
    lateinit var sharedPreference: CustomSharedPreference
    private var mSocket: Socket? = null
//    var obj1:GetRandomPlayerModelData? = null
//    var obj2:ListofOpponentModel? = null

    var click: GetTournamentsListData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindCompetitiveBinding.inflate(LayoutInflater.from(context))
        loading = LoadingDialog(requireContext() as Activity)
        sharedPreference = CustomSharedPreference(requireContext())





        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        StaticFields.toastClass("Please wait, while we are matching opponent for you.")

        countdownTimer()
        userNameandImg()

        val bundle = arguments
        if (bundle != null) {



            click = Gson().fromJson(bundle.getString("GET_MATCHES_OBJ"),GetTournamentsListData::class.java)



            binding.entryFee.text = click?.entryFee!!
            binding.pCount.text = "${click?.playerCount!!} players"
        }



        if (!(StaticFields.isNetworkConnected(requireContext()))) {
            StaticFields.toastClass("Check your network connection")
        } else {
            connectSocket()
        }




        binding.recycleListOpponent.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        rvAdapter = AdapterOpponents(object : AdapterOpponents.OnItemClickListenerHandler {
            override fun onItemClicked(click: ListofOpponentModel, position: Int) {
            }
        })


        binding.recycleListOpponent.adapter = rvAdapter




        binding.exitMatch.setOnClickListener {

            leavePlayer()
            findNavController().popBackStack()

        }

        binding.cancelMatch.setOnClickListener {

            leavePlayer()
            findNavController().popBackStack()
        }

//        binding.beginMatch.setOnClickListener {
//
//            if (!(obj1?.IsPlayable!! && click?.playerCount?.toInt() == obj1?.playerCount)) {
//
//            }
//
//
//            }

//        if (!(URLConstant.tournamentId.isNullOrEmpty())) {
//
//            t_id = URLConstant.tournamentId
//
//        }
//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            u_id = user?.userId
//        loading.startLoading()
////        selectRandomplayer()
    }


    private fun countdownTimer() {
        time = object : CountDownTimer(180000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {

                //Convert milliseconds into hour,minute and seconds
                //Convert milliseconds into hour,minute and seconds
                val hms = java.lang.String.format(
                    "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),

                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                )
                binding.countdown.text = hms //set text


//                binding.countdown.text = ""+millisUntilFinished / 1000
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                showDialog("Request Timeout, Try again. ", "", 0)

            }
        }.start()
    }

    private fun userNameandImg() {
        if (sharedPreference.returnValue("USERIMG") != null
            && sharedPreference.returnValue("USERNAME") != null
        ) {
            binding.userImg.load(sharedPreference.returnValue("USERIMG")) {
                placeholder(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
            }
            //underline text
            val mSpannableString =
                SpannableString(sharedPreference.returnValue("USERNAME") ?: "Name")
            mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
            binding.userName.text = mSpannableString

        }
    }


    private fun showDialog(t: String, h: String, check: Int) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogDepositBinding.inflate(layoutInflater)
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        if (check == 0) {
            dialogBinding.h1.visibility = View.GONE
            dialogBinding.para.text = t
            dialogBinding.price.visibility = View.GONE
            dialogBinding.cancelButton.visibility = View.GONE
            dialogBinding.cancelButton2.text = "Continue Searching"
            dialogBinding.cancelButton2.visibility = View.VISIBLE
            dialogBinding.okButton.text = "return to Play Screen"
        } else {
            dialogBinding.h1.text = h
            dialogBinding.para.text = t
            dialogBinding.price.text = " Entry Fee: ${click?.entryFee}"
        }

        dialogBinding.cancelButton2.setOnClickListener {
            countdownTimer()
            dialog.dismiss()
        }
        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.okButton.setOnClickListener {
            if (check == 0) {
                findNavController().popBackStack()
            } else {
                loading.startLoading()
                participateInTournament()
            }


            dialog.dismiss()

        }

        dialog.show()
    }


    private fun participateInTournament() {
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.participateTournament(
                URLConstant.u_id!!,
                click?.tournamentID!!,
                "abcd",
                object : NetworkListener<ForgotModel> {
                    override fun successFul(t: ForgotModel) {
                        loading.isDismiss()

                        activity?.runOnUiThread {

                            if (t.status == 1) {


                                StaticFields.toastClass(t.message)
                                findNavController().navigate(R.id.action_findCompetitiveFragment_to_fragmentMatchScore)


//                                if (t.isParticipated) {
//                                    StaticFields.toastClass(t.message)
//                                    findNavController().navigate(R.id.action_dashboardActivity_to_pendingRequestFragment)
//
//
//                                } else {
//                                    StaticFields.toastClass(t.message)
//                                    findNavController().navigate(R.id.action_dashboardActivity_to_findCompetitiveFragment)
//
//                                }

                            } else {
                                activity?.runOnUiThread {

                                    StaticFields.toastClass(t.message)
//                                    findNavController().navigate(R.id.action_dashboardActivity_to_pendingRequestFragment)

                                }
                            }

                        }


                    }

                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Api syncing fail participation")
                        }
                    }
                }
            )
        }

    }


    private fun connectSocket() {
        loading.startLoading()
        SocketHandler.setSocket()
        mSocket = SocketHandler.getSocket()
        SocketHandler.establishConnection()

        mSocket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
            Log.e(
                "error",
                "onCreate:${args.contentToString()} "
            )
        }



        mSocket?.on(Socket.EVENT_CONNECT) { args ->
            Log.e("connect", "onCreate: ")

            loading.isDismiss()

            joinPlayer()

            mSocket?.on(
                "getMatchInfo"
            ) { args ->

                activity?.runOnUiThread {


                    try {
//                        receivedChat = ArrayList()

//                    val json = (args[0] as JSONObject).get("data") as JSONObject

                        val json = (args[0] as JSONObject) as JSONObject
//                        roomID = (args[0] as JSONObject).get("roomId") as Int
                        val gson = Gson()

                        val obj1 =
                            gson.fromJson(json.toString(), GetRandomPlayerModelData::class.java)





                        URLConstant.matchId =
                            obj1?.matchID //is match id pr report hona hai match lkn hum getmatches record k response ki match id pr report kr rahy jo k thk hai hai
//                        opponentList.addAll(t.data.listOfOpponents)
//                        rvAdapter.setData(opponentList)
//                        StaticFields.toastClass(t.message)
                        if (click?.gamePlay == 1) {


                            if (click!!.isPractice == true) {

                                if (obj1?.IsPlayable!! && click?.playerCount?.toInt() == obj1?.playerCount) {
                                    time.cancel()
                                    loading.startLoading()
                                    participateInTournament()
                                }
//                                else{
//                                    opponentList = ArrayList()
//                                    loading.isDismiss()
//                                    selectRandomplayer()
//                                }
                            } else {
                                if (obj1?.IsPlayable!! && click?.playerCount?.toInt() == obj1?.playerCount) {
                                    time.cancel()
                                    binding.beginMatch.setOnClickListener {

                                        showDialog(
                                            "You need to deposit following amount in order to proceed further.",
                                            "Deposit",
                                            1
                                        )
                                    }
                                }
//                                else {
//                                    opponentList = ArrayList()
//                                    loading.isDismiss()
//                                    selectRandomplayer()
//                                }
                            }


                        }
                        else if (click?.gamePlay == 2) {


                            if (obj1?.IsPlayable!!) {
                            time.cancel()
                                StaticFields.toastClass("Its a Non-Live Match. Press button to play match.")

                                binding.beginMatch.setOnClickListener {
                                    showDialog(
                                        "You need to deposit following amount in order to proceed further.",
                                        "Deposit",
                                        1
                                    )
                                }
                            }
//                            else{
//                                opponentList = ArrayList()
//                                loading.isDismiss()
//                                selectRandomplayer()
//                            }

//                            URLConstant.playerId = t.data.listOfOpponents
//
                        }
//                            loading.isDismiss()
                    } catch (e: Exception) {
                        Log.e(
                            "exception=",
                            "$e"
                        )
                    }

                }

                Log.e(
                    "retrievematchInfoSocket=",
                    "onCreate:${args.contentToString()} "
                )
//                comments.addAll(args)

//                    val mJsonString = "..."
//                    val parser = JsonParser()
//                    val mJson = JsonParser.parse(item as JsonObject)
//                    val gson = Gson()
//                    val `object`: MyDataObject = gson.fromJson(mJson, MyDataObject::class.java)

            }




            mSocket?.on(
                "getRandomPlayer"
            ) { args ->

                activity?.runOnUiThread {
                    try {

                        val json = (args[0] as JSONArray) as JSONArray

                        opponentList = ArrayList()

                        for (index in 0 until json.length()) {

                            val obj = Gson().fromJson(
                                json.getJSONObject(index).toString(),
                                ListofOpponentModel::class.java
                            )

                            if (obj.opponentId != URLConstant.u_id) {
                                opponentList.add(obj)
                            }

                        }
                        if (click?.gamePlay == 1){
                            if (opponentList.size > 0) {
                                StaticFields.toastClass("New Player has joined")
                            } else if (opponentList.size == 0) {
                                StaticFields.toastClass("No Player at the moment, please wait")

                            }

                            rvAdapter.setData(opponentList)

                        }
                        else if(click?.gamePlay == 2){
                            opponentList = ArrayList()
                             if (opponentList.size == 0) {
                                StaticFields.toastClass("Its a Non-Live Match. Press button to play match.")
                            }
                            rvAdapter.setData(opponentList)

                        }


//                        if (opponentList[index].isLeave!!) {
//                            for (item in opponentList) {
//
//                                if (obj2?.opponentId == item.opponentId) {
//
//                                    opponentList.remove(item)
//                                    rvAdapter.setData(opponentList)
////                                    loading.isDismiss()
//
////                                rvAdapter.notifyDataSetChanged()
//                                }
//                            }
//                        } else {
//                            if (obj2.opponentId != URLConstant.u_id){
//                                opponentList.add(obj2!!)
//                                rvAdapter.setData(opponentList)
////                                loading.isDismiss()
//                            }
//                            loading.isDismiss()


//                        rvAdapter.notifyDataSetChanged()




                    } catch (e: Exception) {
                        Log.e(
                            "exception=",
                            "$e"
                        )

                    }

                }

                Log.e(
                    "retrieveOpponentSocket",
                    "onCreate:${args.contentToString()} "
                )


            }

        }

    }


    private fun joinPlayer() {
        if (mSocket?.connected() == true) {
            Log.e("connected", "onCreate:bami ")


            val checked = WebSocketJoinLeaveModel(
                if (URLConstant.eventId?.isNotEmpty() == true) {
                    URLConstant.eventId
                } else {
                    click?.tournamentID!!
                },
                URLConstant.u_id!!,
                )
//            val map = HashMap<String, Any>()
//            map["gameId"] = "00000067"
//            map["userId"] = "62ebd0aa2a494e2a1260777f"
//            map["type"] = 0
//
//            val obj = abcd()
//            obj.gameId = "00000067"
//            obj.userId = "62ebd0aa2a494e2a1260777f"
//            obj.type = 0
            val gson: JsonObject = JsonParser.parseString(Gson().toJson(checked)).asJsonObject

//            val jsonObj = Gson().toJson(map)
//            Log.e("JSONOBJ", "onCreate:$jsonObj " )

            mSocket?.emit("joinPlayer", gson)
//            mSocket?.emit("joinRoom", map)
//            map.clear()
        } else {
            Log.e("notconnected", "onCreate:bami ")
        }

    }


    private fun leavePlayer() {
        if (mSocket?.connected() == true) {
            Log.e("connected", "onCreate:bami ")


            val checked = WebSocketJoinLeaveModel(
                if (URLConstant.eventId?.isNotEmpty() == true) {
                    URLConstant.eventId
                } else {
                    click?.tournamentID!!
                },
                URLConstant.u_id!!
                )
//            val map = HashMap<String, Any>()
//            map["gameId"] = "00000067"
//            map["userId"] = "62ebd0aa2a494e2a1260777f"
//            map["type"] = 0
//
//            val obj = abcd()
//            obj.gameId = "00000067"
//            obj.userId = "62ebd0aa2a494e2a1260777f"
//            obj.type = 0
            val gson: JsonObject = JsonParser.parseString(Gson().toJson(checked)).asJsonObject

//            val jsonObj = Gson().toJson(map)
//            Log.e("JSONOBJ", "onCreate:$jsonObj " )

            mSocket?.emit("leavePlayer", gson)
//            mSocket?.emit("joinRoom", map)
//            map.clear()
        } else {
            Log.e("notconnected", "onCreate:bami ")
        }

    }


    private fun selectRandomplayer() {

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getRandomPlayer(
                URLConstant.u_id!!,
                click?.tournamentID!!,
                object : NetworkListener<GetRandomPlayerModel> {
                    override fun successFul(t: GetRandomPlayerModel) {
                        loading.isDismiss()

                        activity?.runOnUiThread {


                            if (t.status == 1) {
                                // same match id hai ye or getmatched record wali
                                URLConstant.matchId =
                                    t.data.matchID //is match id pr report hona hai match lkn hum getmatches record k response ki match id pr report kr rahy jo k thk hai hai
                                opponentList.addAll(t.data.listOfOpponents)
                                rvAdapter.setData(opponentList)
                                StaticFields.toastClass(t.message)
                                if (click?.gamePlay == 1) {

                                    if (click!!.isPractice == true) {

                                        if (t.data.IsPlayable && click?.playerCount?.toInt() == t.data.listOfOpponents.size + 1) {
                                            time.cancel()
                                            loading.startLoading()
                                            participateInTournament()
                                        } else {
                                            opponentList = ArrayList()
                                            loading.isDismiss()
                                            selectRandomplayer()
                                        }
                                    } else {
                                        if (t.data.IsPlayable && click?.playerCount?.toInt() == t.data.listOfOpponents.size + 1) {
                                            time.cancel()
                                            binding.beginMatch.setOnClickListener {

                                                showDialog(
                                                    "You need to deposit following amount in order to proceed further.",
                                                    "Deposit",
                                                    1
                                                )
                                            }
                                        } else {
                                            opponentList = ArrayList()
                                            loading.isDismiss()
                                            selectRandomplayer()
                                        }
                                    }


                                } else if (click?.gamePlay == 2) {

                                    if (t.data.IsPlayable) {
                                        time.cancel()

                                        binding.beginMatch.setOnClickListener {
                                            showDialog(
                                                "You need to deposit following amount in order to proceed further.",
                                                "Deposit",
                                                1
                                            )
                                        }
                                    } else {
                                        opponentList = ArrayList()
                                        loading.isDismiss()
                                        selectRandomplayer()
                                    }

//                            URLConstant.playerId = t.data.listOfOpponents
//
                                }
                            } else {
//                                time.cancel()
                                showDialog(t.message, "", 0)


                            }
                        }
                    }


                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("api syncing failed random")
                        }

                    }
                }
            )
        }
    }


    override fun onPause() {

        leavePlayer()
        mSocket?.disconnect()

        super.onPause()


    }
}




