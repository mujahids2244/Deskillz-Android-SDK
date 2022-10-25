package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentSignInBinding
import com.arhamsoft.deskilz.databinding.FragmentSignUpBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.SignUpModel
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    lateinit var loading:LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        loading = LoadingDialog(requireContext() as Activity)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerBtn.setOnClickListener {

            registerApiCalling()

        }

        binding.gotoSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.backToSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
    }


//    private fun responseApiLiveData() {
//
//        NetworkRepo.signupsuccessLiveData.observe(viewLifecycleOwner ){
//
//            if(it.success == 1){
//                loading.isDismiss()
//
//                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
//
//                findNavController().popBackStack()
//            }
//            else{
//                loading.isDismiss()
//                Toast.makeText(requireContext(), "${it.message}.", Toast.LENGTH_SHORT).show()
//                findNavController().popBackStack()
//
//
//            }
//        }
//
//        NetworkRepo.errorLiveData.observe(viewLifecycleOwner){
//
//            if (it.status == 1) {
//
//                loading.isDismiss()
//
//                Toast.makeText(
//                    requireContext(),
//                    "api syncing failed signup ${it.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//                it.status = 0
//            }
//
//        }
//    }


    fun registerApiCalling(){



        if (binding.mail.text!!.isEmpty() && binding.password.text!!.isEmpty() && binding.repassword.text!!.isEmpty()) {
            Toast.makeText(requireContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show()

        } else {
            if (binding.mail.text!!.isEmpty()) {
                binding.mail.requestFocus()
                binding.mail.error = "Email Field is Empty"
            }
            else if (binding.password.text!!.isEmpty()) {
                binding.password.requestFocus()
                binding.password.error = "pass Field is Empty"

            } else if (binding.repassword.text!!.isEmpty()) {
                binding.repassword.requestFocus()
                binding.repassword.error = "repass Field is Empty"

            } else if (binding.password.text.toString() != binding.repassword.text.toString()) {
                Toast.makeText(requireContext(), "You Have Entered a Wrong Re-Password", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.password.text.toString() == binding.repassword.text.toString()) {

//                NetworkRepo.signupsuccessLiveData = MutableLiveData()

                loading.startLoading()
                CoroutineScope(Dispatchers.IO).launch {

                    NetworkRepo.register(
                        binding.username.text.toString(),
                        binding.mail.text.toString(),
                        binding.password.text.toString(),
                        "Pakistan",
                        object : NetworkListener<SignUpModel> {
                            override fun successFul(t: SignUpModel) {

                                activity?.runOnUiThread {
                                    if (t.success == 1) {
                                        loading.isDismiss()

                                        StaticFields.toastClass(t.message)

                                        findNavController().popBackStack()
                                    } else {
                                        loading.isDismiss()
                                        StaticFields.toastClass(t.message)
                                        findNavController().popBackStack()


                                    }
                                }

                            }

                            override fun failure() {
                                activity?.runOnUiThread {

                                    loading.isDismiss()
                                    StaticFields.toastClass("api syncing failed signup")
                                }
                            }
                        }
                    )
                }

            }


        }

    }
}