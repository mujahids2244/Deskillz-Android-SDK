package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentRedeemPointsBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.ChatPost
import com.arhamsoft.deskilz.networking.networkModels.NotificationModel
import com.arhamsoft.deskilz.networking.networkModels.RedeemPointsModel
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RedeemPointsFragment : Fragment() {

    lateinit var binding: FragmentRedeemPointsBinding
    lateinit var loading:LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRedeemPointsBinding.inflate(layoutInflater)

        loading = LoadingDialog(requireContext() as Activity)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

  binding.cancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.icPt.text =  URLConstant.points


            binding.convertPoints.setOnClickListener {

                if (binding.points.text.isEmpty()){
                    binding.points.error = "Enter points"
                }
                else{
                    if (URLConstant.points!!.toLong() > 1000){

                        if (!(binding.points.text.toString().toLong() >= 1000L)){
                            StaticFields.toastClass("Entered points are not enough according to current rate")
                        }
                        else{
                            loading.startLoading()
                            getRedeemPoints()

                        }

                    }
                    else{
                        StaticFields.toastClass("You don't have enough Points")
                    }

                }



        }




        return binding.root
    }


    private fun getRedeemPoints(){

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.redeemPoints(
                URLConstant.u_id!!,
                binding.points.text.toString(),
                object : NetworkListener<RedeemPointsModel> {
                    override fun successFul(t: RedeemPointsModel) {
                        activity?.runOnUiThread {

                        loading.isDismiss()
                        if (t.status == 1) {


                            StaticFields.toastClass(t.message)

                                binding.icPt.text = t.data.remianingLoyaltyPoints.toString()

                                }
                            else{
                            StaticFields.toastClass(t.message)

                        }



                            }


                    }
                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Api syncing fail get notifications")
                        }
                    }
                }
            )
        }

    }


}