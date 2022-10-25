package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alphaCareInc.app.room.UserDatabase
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentResetPasswordBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.ForgotModel
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ResetPasswordFragment : Fragment() {

    lateinit var binding: FragmentResetPasswordBinding
    var u_id:String? = ""
    lateinit var loading: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentResetPasswordBinding.inflate(inflater)
        loading = LoadingDialog(requireContext() as Activity)



        binding.backToAccount.setOnClickListener {
            findNavController().popBackStack()
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            if (user != null) {
//                u_id = user.userId
//            }
//        }


        binding.loginBtn.setOnClickListener {
            if (binding.newPassword.text.isEmpty() && binding.retypeNewPassword.text.isEmpty() && binding.currentPassword.text.isEmpty()) {
                binding.newPassword.error = "Email Field is Empty"
                binding.retypeNewPassword.error = "Password Field is Empty"
                binding.currentPassword.error = "Password Field is Empty"
            } else if (binding.newPassword.text!!.isEmpty()) {
                binding.newPassword.requestFocus()
                binding.newPassword.error = "New Password Field is Empty"

            } else if (binding.retypeNewPassword.text!!.isEmpty()) {
                binding.retypeNewPassword.requestFocus()
                binding.retypeNewPassword.error = "Re-type Password Field is Empty"

            }
            else if (binding.currentPassword.text!!.isEmpty()) {
                binding.currentPassword.requestFocus()
                binding.currentPassword.error = " Current Password Field is Empty"

            }
            else{
                if (binding.newPassword.text.toString() == binding.retypeNewPassword.text.toString()){
                    loading.startLoading()
                    callResetPasswordApi()
                }
                else{
                    binding.newPassword.error = "Password mismatch"
                    binding.retypeNewPassword.error = "Password mismatch"
                }
            }


        }



        return binding.root
    }



    private fun callResetPasswordApi(){
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.changePassword(
                URLConstant.u_id!!,
                binding.currentPassword.text.toString(),
                binding.newPassword.text.toString(),
                object : NetworkListener<ForgotModel> {
                    override fun successFul(t: ForgotModel) {
                        loading.isDismiss()
                        activity?.runOnUiThread {

                        if (t.status==1){
                                StaticFields.toastClass(t.message)
                                findNavController().navigate(R.id.action_resetPasswordFragment_to_accountSettingFragment)
                            }
                            else{
                                StaticFields.toastClass(t.message)
                        }

                        }

                    }

                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("api syncing failed resetPassword")
                        }
                    }
                }
            )
        }

    }

}