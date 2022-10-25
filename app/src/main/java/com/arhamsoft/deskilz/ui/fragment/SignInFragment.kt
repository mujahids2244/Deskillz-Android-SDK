package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alphaCareInc.app.room.User
import com.alphaCareInc.app.room.UserDatabase
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentSignInBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.ForgotModel
import com.arhamsoft.deskilz.networking.networkModels.LoginModel
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    lateinit var loadingDialog: LoadingDialog
    private lateinit var database: UserDatabase
    private var device_id: String? = null
    lateinit var sharedPreference: CustomSharedPreference
    private var fcmToken:String? = null
    var id:Int?=0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        database = UserDatabase.getDatabase(requireContext())
        loadingDialog = LoadingDialog(requireContext() as Activity)
        sharedPreference = CustomSharedPreference(requireContext())

//        val notif = activity?.applicationContext?.applicationContext
//            ?.getSystemService(Context.NOTIFICATION_SERVICE)
//
//        NotificationManagerCompat.from(requireContext()).cancelAll()
        cancelNotification()

        StaticFields.fcmToken()
//        id = sharedPreference.returnCurrentLoginID("user")
//
//
//        runBlocking {
//            val user  = UserDatabase.getDatabase(requireContext()).userDao().getUser(id!!)
//            if (user != null) {
//                URLConstant.u_id = user.userId
//            }
//        }


        fcmToken = sharedPreference.returnValue("TOKEN")

        device_id = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
        sharedPreference.saveValue("DEVICE_ID",device_id!!)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        binding.loginBtn.setOnClickListener {
            loginApiCall()
        }


        binding.forgot.setOnClickListener {
            forgotDialog()
        }

        binding.gotoSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }

    fun cancelNotification() {
        val ns = NOTIFICATION_SERVICE
        val nMgr = activity!!.applicationContext.getSystemService(ns) as NotificationManager
        if(nMgr.activeNotifications.isNotEmpty()){
            nMgr.cancelAll()

        }
    }

    private fun loginApiCall() {

        if (binding.mail.text!!.isEmpty() && binding.password.text!!.isEmpty()) {
            binding.mail.error = "Email Field is Empty"
            binding.password.error = "Password Field is Empty"
        } else if (binding.mail.text!!.isEmpty()) {
            binding.mail.requestFocus()
            binding.mail.error = "Email Field is Empty"

        } else if (binding.password.text!!.isEmpty()) {
            binding.password.requestFocus()
            binding.password.error = "Password Field is Empty"

        } else if (!(isValidEmail(binding.mail.text.toString()))) {
            binding.mail.error = "Email Format is Incorrect"
            binding.mail.requestFocus()

        } else {


            loadingDialog.startLoading()
            CoroutineScope(Dispatchers.IO).launch {

                NetworkRepo.login(
                    binding.mail.text.toString(),
                    binding.password.text.toString(),
                    device_id!!,
                    fcmToken!!,
                    object : NetworkListener<LoginModel> {
                        override fun successFul(t: LoginModel) {
                            activity?.runOnUiThread {

                                if (t.status == 1) {

                                    if (t.data.userID == URLConstant.u_id){
                                        loadingDialog.isDismiss()
                                        StaticFields.toastClass("Current User Already Login")

                                    }
                                    else{


                                    val user = User()
                                    user.accessToken = t.data.authToken
                                    user.userId = t.data.userID
                                    user.userName = t.data.userName
                                    user.userEmail = binding.mail.text.toString()
                                    insertData(user)

                                    StaticFields.toastClass(t.message)

                                    sharedPreference.saveValue("USERIMG", t.data.userImage)
                                    sharedPreference.saveValue("USERNAME", t.data.userName)

//                URLConstant.userName = it.data.userName
//                URLConstant.userImg = it.data.userImage

                                    sharedPreference.saveLogin("LOGIN", true)
                                    //RetrofitClient.updateInstance()

                                    loadingDialog.isDismiss()

                                    findNavController().navigate(R.id.action_signInFragment_to_dashboardActivity)

//                if (user.code == 0)
//                    sharedPreference.saveCodeCheck("CODE", true)


//                                    NetworkRepo.loginsuccessLiveData = MutableLiveData()

                                    }

                                } else {
                                    loadingDialog.isDismiss()
                                    StaticFields.toastClass(t.message)

                                }
                            }
                        }

                        override fun failure() {
                            activity?.runOnUiThread {

                                loadingDialog.isDismiss()
                                StaticFields.toastClass("api syncing failed login")

                            }
                        }
                    }
                )
            }
        }
    }

//    private fun responseApiLiveData() {
//
//        NetworkRepo.loginsuccessLiveData.observe(viewLifecycleOwner) {
//
//
//        }
//
//
//
//
//        NetworkRepo.errorLiveData.observe(viewLifecycleOwner) {
//
//            if (it.status == 1) {
//
//                loadingDialog.isDismiss()
//
//                Toast.makeText(
//                    requireContext(),
//                    "api syncing failed login ${it.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//                it.status = 0
//            }
//
//        }
//    }


    fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }


    private fun forgotDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.forgot_dialog)

        val yesBtn = dialog.findViewById(R.id.submit_button) as RelativeLayout
        val noBtn = dialog.findViewById(R.id.cancel_button) as RelativeLayout

        yesBtn.setOnClickListener {


                forgotPassApiCall()

        }
        noBtn.setOnClickListener {
            dialog.dismiss() }
        dialog.show()

    }

    private fun insertData(user: User) {

        val th = Thread(Runnable {
            val already = database.userDao().getPreviousUser(user.userId!!)

            if (already == null){
                val returnId = database.userDao().insertUser(user)
                URLConstant.currentLoginId = returnId.toInt()
            }
            else{
                already.userName = user.userName
                already.userEmail = user.userEmail
                already.accessToken = user.accessToken
                database.userDao().updateUser(already)
                URLConstant.currentLoginId = already.id
            }
            sharedPreference.saveCurrentLoginID("user",URLConstant.currentLoginId)

            NetworkRepo.updateRetrofitClientInstance()
        })
        th.start()
        th.join()
    }
//
//    private fun getpreviousData(id:String) {
//
//        val th = Thread(Runnable {
//            NetworkRepo.updateRetrofitClientInstance()
//        })
//        th.start()
//        th.join()
//    }



    fun forgotPassApiCall(){

        loadingDialog.startLoading()
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.forgot(
                binding.mail.text.toString(),
                device_id!!,
                object : NetworkListener<ForgotModel> {
                    override fun successFul(t: ForgotModel) {
                        activity?.runOnUiThread {

                            loadingDialog.isDismiss()

                            if (t.status == 1) {
                                StaticFields.toastClass(t.message)
//                findNavController().navigate(R.id.action_forgotFragment_to_signInFragment)
                            } else {
                                StaticFields.toastClass(t.message)

                            }
                        }
                    }

                    override fun failure() {
                        activity?.runOnUiThread {

                            loadingDialog.isDismiss()
                            StaticFields.toastClass("api syncing failed forgot pass")

                        }                    }
                }
            )
        }

    }



}