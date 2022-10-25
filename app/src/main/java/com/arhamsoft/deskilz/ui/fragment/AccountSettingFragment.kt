package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentAccountSettingBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.ForgotModel
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AccountSettingFragment : Fragment() {

    lateinit var binding:FragmentAccountSettingBinding
    lateinit var loading:LoadingDialog
    lateinit var sharedPreference: CustomSharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAccountSettingBinding.inflate(layoutInflater)

        loading = LoadingDialog(requireContext() as Activity)
        sharedPreference = CustomSharedPreference(requireContext())


        binding.gotoUserProfile.setOnClickListener {
            findNavController().navigate(R.id.action_accountSettingFragment_to_profileFragment)
        }
        binding.changeUsername.setOnClickListener {
            findNavController().navigate(R.id.action_accountSettingFragment_to_updateProfileFragment)
        }

        binding.changePassword.setOnClickListener {
            findNavController().navigate(R.id.action_accountSettingFragment_to_resetPasswordFragment)
        }

        binding.redeemPoints.setOnClickListener {
            findNavController().navigate(R.id.action_accountSettingFragment_to_redeemPointsFragment)
        }

        binding.switchacc.setOnClickListener {

            findNavController().navigate(R.id.action_accountSettingFragment_to_switchAccountFragment)
//            loading.startLoading()
//            logoutUser()
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun logoutUser(){
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.logout(
                URLConstant.u_id!!,
                object : NetworkListener<ForgotModel> {
                    override fun successFul(t: ForgotModel) {
                        loading.isDismiss()
                        activity?.runOnUiThread {
                            if (t.status == 1) {

                                StaticFields.toastClass(t.message)
                                sharedPreference.clearSharedPreference()
                                findNavController().navigate(R.id.action_accountSettingFragment_to_signInFragment)

                            }
                            else{
                                StaticFields.toastClass(t.message)

                            }
                        }
                    }

                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("api failure logout")
                        }
                    }
                })
        }
    }


}