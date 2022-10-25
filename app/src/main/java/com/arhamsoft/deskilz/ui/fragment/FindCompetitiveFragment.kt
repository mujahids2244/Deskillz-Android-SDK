package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.DialogDepositBinding
import com.arhamsoft.deskilz.databinding.FragmentFindCompetitiveBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.*
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.ui.adapter.AdapterOpponents
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    var click :GetTournamentsListData? = null

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

        countdownTimer()
        userNameandImg()

        val bundle = arguments
        if (bundle != null) {

            click = bundle.getSerializable("GET_MATCHES_OBJ") as GetTournamentsListData

            binding.entryFee.text = click?.entryFee
            binding.pCount.text = "${click?.playerCount} players"
        }




        rvAdapter = AdapterOpponents(object : AdapterOpponents.OnItemClickListenerHandler {
            override fun onItemClicked(click: ListofOpponentModel, position: Int) {
            }
        })


        binding.recycleListOpponent.adapter = rvAdapter




        binding.exitMatch.setOnClickListener {

            findNavController().popBackStack()
        }

//        if (!(URLConstant.tournamentId.isNullOrEmpty())) {
//
//            t_id = URLConstant.tournamentId
//
//        }
//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            u_id = user?.userId
        loading.startLoading()
        selectRandomplayer()
    }


    private fun countdownTimer() {
        time = object : CountDownTimer(30000, 1000) {

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
                showDialog("Request Timeout. No player found at the moment. Try again later", "", 0)

            }
        }.start()
    }

    private fun userNameandImg(){
        if (sharedPreference.returnValue("USERIMG") != null
            && sharedPreference.returnValue("USERNAME") != null) {
            binding.userImg.load(sharedPreference.returnValue("USERIMG")) {
                placeholder(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
            }
            //underline text
            val mSpannableString = SpannableString(sharedPreference.returnValue("USERNAME") ?: "Name")
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
            dialogBinding.okButton.text = "return to Play Screen"
        } else {
            dialogBinding.h1.text = h
            dialogBinding.para.text = t
            dialogBinding.price.text = " Entry Fee: ${click?.entryFee}"
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
                                URLConstant.matchId = t.data.matchID //is match id pr report hona hai match lkn hum getmatches record k response ki match id pr report kr rahy jo k thk hai hai
                                opponentList.addAll(t.data.listOfOpponents)
                                rvAdapter.setData(opponentList)
                                StaticFields.toastClass(t.message)
                                if (click?.gamePlay == 1) {

                                    if(click!!.isPractice == true){

                                        if (t.data.IsPlayable && click?.playerCount?.toInt() == t.data.listOfOpponents.size +1){
                                            time.cancel()
                                            loading.startLoading()
                                            participateInTournament()
                                        }
                                        else{
                                            opponentList = ArrayList()
                                            loading.isDismiss()
                                            selectRandomplayer()
                                        }
                                    }
                                    else {
                                        if (t.data.IsPlayable && click?.playerCount?.toInt() == t.data.listOfOpponents.size + 1) {
                                            time.cancel()
                                            binding.beginMatch.setOnClickListener {

                                                showDialog(
                                                    "You need to deposit following amount in order to proceed further.",
                                                    "Deposit",
                                                    1
                                                )
                                            }
                                        }
                                        else {
                                            opponentList = ArrayList()
                                            loading.isDismiss()
                                            selectRandomplayer()
                                        }
                                    }


                                }

                                else if (click?.gamePlay == 2) {

                                    if (t.data.IsPlayable) {
                                        time.cancel()

                                        binding.beginMatch.setOnClickListener {
                                            showDialog(
                                                "You need to deposit following amount in order to proceed further.",
                                                "Deposit",
                                                1
                                            )
                                        }
                                    }
                                    else{
                                        opponentList = ArrayList()
                                        loading.isDismiss()
                                        selectRandomplayer()
                                    }

//                            URLConstant.playerId = t.data.listOfOpponents
//
                                }
                            }


                            else {
                                time.cancel()
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


}