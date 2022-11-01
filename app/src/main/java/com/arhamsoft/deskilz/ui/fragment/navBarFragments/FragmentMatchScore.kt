package com.arhamsoft.deskilz.ui.fragment.navBarFragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import coil.load
import com.alphaCareInc.app.room.UserDatabase
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentMatchScoreBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.*
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentMatchScore : Fragment() {

    lateinit var binding:FragmentMatchScoreBinding
    lateinit var loading:LoadingDialog
    var u_id:String? =null
    var progressionList:ArrayList<ProgressPost> = ArrayList()
    var gameCustomDataList: ArrayList<CustomPlayerModelData> = ArrayList()
    private lateinit var prog:HashMap<*,*>
    val someActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            URLConstant.score= data?.extras?.get("matchScore") as Long
            prog =  data.extras?.get("progression") as HashMap<*,*>
            Log.d("dataResult", data.toString())

            loading.startLoading()

            getGameCustomData()


        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMatchScoreBinding.inflate(LayoutInflater.from(context))
        loading = LoadingDialog(requireContext() as Activity)

        val myClass = Class.forName(URLConstant.gameActivity)
        val intent = Intent(requireContext(), myClass)
        someActivity.launch(intent)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding.btnSubmit.setOnClickListener {
//            findNavController().navigate(R.id.)
            loading.startLoading()

            updateScore()

        }




        }


    private fun getGameCustomData(){
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getGameCustomData(
                object : NetworkListener<CustomPlayerModel> {
                    override fun successFul(t: CustomPlayerModel) {
                        activity?.runOnUiThread {
                            loading.isDismiss()

                            if (t.status == 1) {


                                StaticFields.toastClass(t.message)

                                gameCustomDataList.addAll(t.data)

//                                val map = HashMap<String, Any>()
//                                map["AA1"] = 100
//                                map["test1"] = 200


                                for (item in gameCustomDataList){
                                    for (i in prog.keys){
                                        if (item.keyName == i){

                                            progressionList.add(ProgressPost(item.keyName,prog[i]!!))

                                        }
                                    }


                                }
                                progressionData()


//                            activity?.runOnUiThread {
//                                StaticFields.toastClass("api chal ri hai ")
//                            }

//                            if (t.data.isNotEmpty()) {
//                                activity?.runOnUiThread {
//
//                                    binding.eventLayout.visibility = View.VISIBLE
//
//                                    eventList.addAll(t.data)
//
//                                    rvAdapterEvents.addData(eventList)
//                                }
//                            }
                            }
                            else{
                                StaticFields.toastClass(t.message)
                            }
                        }



                    }


                    override fun failure() {

                        activity?.runOnUiThread {
                            loading.isDismiss()

                            StaticFields.toastClass("Api syncing fail getCustomgameDAta")
                        }
                    }
                }
            )
        }

    }



    private fun updateScore() {

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.updateScore(
                URLConstant.score,
                URLConstant.u_id!!,
                URLConstant.matchId!!,
                object : NetworkListener<UpdateMatchScoreModel> {
                    override fun successFul(t: UpdateMatchScoreModel) {
                        loading.isDismiss()
                        activity?.runOnUiThread {

                        if (t.status ==1) {
                            StaticFields.toastClass(t.message)
                            findNavController().navigate(R.id.action_fragmentMatchScore_to_dashboardActivity)

//                                binding.score.text = t.data.matchScore
                        }
                        else{
                            StaticFields.toastClass(t.message)
                        }

                        }

                    }

                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("api syncing failed update score")
                        }
                    }
                }
            )}

    }


    private fun progressionData() {

//        progressionList.add(ProgressPost("Date",32))
//        progressionList.add(ProgressPost("bool",true))
//        progressionList.add(ProgressPost("dt",2022-12-19))
//        progressionList.add(ProgressPost("strin","32"))

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getProgressionData(
                progressionList,
                object : NetworkListener<ProgressionModel> {
                    override fun successFul(t: ProgressionModel) {
                        loading.isDismiss()
                        activity?.runOnUiThread {
                            if (t.status ==1){
                                StaticFields.toastClass("progression hit")
                                binding.icTopImg.load(t.data.badgeImage)
                                binding.icSmallImg.load(t.data.entryPointImage)
                                binding.icBackImg.load(t.data.backgroundImage)
                                binding.entryPointName.text = t.data.entryPointName
                                binding.text1.text = t.data.title
                                binding.text2.text = t.data.subtitle1
                                binding.text3.text = t.data.subtitle2
                            }
                            else{
                                StaticFields.toastClass(t.message)

                            }
                            Log.e("response", " ${t.data}" )
                        }
                    }

                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("api syncing failed progression")
                        }
                    }
                }
            )}

    }


    override fun onResume() {
        super.onResume()
        binding.score.text = URLConstant.score.toString()

    }
}