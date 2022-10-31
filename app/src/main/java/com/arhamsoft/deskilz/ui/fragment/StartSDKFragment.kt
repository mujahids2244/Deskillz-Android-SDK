package com.arhamsoft.deskilz.ui.fragment

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentStartSdkBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.CustomPlayerModel
import com.arhamsoft.deskilz.networking.networkModels.ForgotModel
import com.arhamsoft.deskilz.networking.networkModels.ProgressPost
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.utils.InternetConLiveData
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

import com.google.android.gms.tasks.OnCompleteListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URI.create

//private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 101
private const val REQUEST_CHECK_SETTINGS = 10001

class StartSDKFragment : Fragment() {

    private lateinit var binding: FragmentStartSdkBinding
    private lateinit var navController: NavController
    private lateinit var loading: LoadingDialog
    private lateinit var connection: InternetConLiveData
    var progressionList:ArrayList<ProgressPost> = ArrayList()
    private var locationRequest: LocationRequest? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartSdkBinding.inflate(layoutInflater)
        loading = LoadingDialog(requireContext() as Activity)
        navController = findNavController()


//        FirebaseApp.initializeApp(requireContext())
        if (!(StaticFields.isNetworkConnected(requireContext()))){
            binding.noint.noInternet.visibility = View.VISIBLE
        }
        coreLoop()


        checkNetworkConnection()


//        StaticFields.fcmToken()

        if(isLocationEnabled(requireContext())) {
            Dexter.withContext(requireContext())
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                        if (p0?.areAllPermissionsGranted() == true) {

                        } else {
                            Toast.makeText(
                                requireContext(),
                                "You have to enable permissions from App settings",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        p1?.continuePermissionRequest()
                    }
                })
                .check()

        }





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        /*CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.login(
                "mujahids999@gmail.com",
                "123456789",
                object: NetworkListener<LoginModel> {
                    override fun successFul(t: LoginModel) {
                        Log.d("ApiResponse", t.message.toString())
                    }

                    override fun failure() {
                        Log.d("ApiResponse", "Error")
                    }
                }
            )
        }*/

        //binding.btnTextPlayNow.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.playNowBtn.setOnClickListener {

            navController.navigate(R.id.action_startSDKFragment_to_loadingActivity)

            /*CoroutineScope(Dispatchers.IO).launch {
                NetworkRepo.logout(
                    StaticFields.userModel!!.data!!.userId,
                    object : NetworkListener<LogoutModel> {
                        override fun successFul(t: LogoutModel) {
                            Log.d("ApiResponse", t.message.toString())
                        }

                        override fun failure() {
                            Log.d("ApiResponse", "Error")
                        }

                    }
                )
            }
            startActivity(Intent(requireContext(), LoadingActivity::class.java))
            finish()*/
        }
    }

    private fun isLocationEnabled(context: Context): Boolean {
        var locationMode = 0
        val locationProviders: String
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            turnOnLocation()

            try {
                locationMode =
                    Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
                turnOnLocation()

            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
//                turnOnLocation()

            }
            locationMode != Settings.Secure.LOCATION_MODE_OFF
        } else {
//            turnOnLocation()
            locationProviders =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.LOCATION_MODE)
            !TextUtils.isEmpty(locationProviders)
        }
    }


    private fun turnOnLocation()  {

        locationRequest = LocationRequest.create()
        locationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest!!.setInterval(5000)
        locationRequest!!.setFastestInterval(2000)
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true)
        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(
            requireContext()
        )
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener(OnCompleteListener<LocationSettingsResponse?> { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                Toast.makeText(requireContext(), "GPS Turned on", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(
                            requireActivity(),
                            REQUEST_CHECK_SETTINGS
                        )
                    } catch (ex: IntentSender.SendIntentException) {
                        ex.printStackTrace()
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            when (resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    Toast.makeText(requireContext(), "GPS is Turned on", Toast.LENGTH_SHORT).show()
                }
                AppCompatActivity.RESULT_CANCELED -> {
                    turnOnLocation()
                }
            }
        }
    }



    private fun checkNetworkConnection(){

        connection = InternetConLiveData(requireContext())

        connection.observe(viewLifecycleOwner) { isConnected ->

            if (isConnected) {
                binding.noint.noInternet.visibility = View.GONE
                coreLoop()

//                loading.startLoading()
//                getGameCustomData()
            }
            else {
                binding.noint.noInternet.visibility = View.VISIBLE

            }
        }

    }

    private fun coreLoop(){
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.coreLoop(
                StaticFields.key,
                object : NetworkListener<ForgotModel> {
                    override fun successFul(t: ForgotModel) {
                        activity?.runOnUiThread {
                            loading.isDismiss()

                            if (t.status == 1) {


                                StaticFields.toastClass(t.message)

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



//    private fun getGameCustomData(){
//        CoroutineScope(Dispatchers.IO).launch {
//            NetworkRepo.getGameCustomData(
//                object : NetworkListener<CustomPlayerModel> {
//                    override fun successFul(t: CustomPlayerModel) {
//                        activity?.runOnUiThread {
//                            loading.isDismiss()
//
//                        if (t.status == 1) {
//
//
//                            StaticFields.toastClass(t.message)
//
//                            URLConstant.gameCustomData= t.data
//
//
//
////                            activity?.runOnUiThread {
////                                StaticFields.toastClass("api chal ri hai ")
////                            }
//
////                            if (t.data.isNotEmpty()) {
////                                activity?.runOnUiThread {
////
////                                    binding.eventLayout.visibility = View.VISIBLE
////
////                                    eventList.addAll(t.data)
////
////                                    rvAdapterEvents.addData(eventList)
////                                }
////                            }
//                        }
//                        else{
//                                StaticFields.toastClass(t.message)
//                            }
//                        }
//
//
//
//                    }
//
//
//                    override fun failure() {
//
//                        activity?.runOnUiThread {
//                            loading.isDismiss()
//
//                            StaticFields.toastClass("Api syncing fail getCustomgameDAta")
//                        }
//                    }
//                }
//            )
//        }
//
//    }



}