package com.arhamsoft.deskilz.ui.fragment

import android.animation.Animator
import android.app.Activity
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentLoadingScreenBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.ThemeModel
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadingActivity : Fragment() {

    private lateinit var binding: FragmentLoadingScreenBinding
    private lateinit var navController: NavController
    private lateinit var sharedPreference: CustomSharedPreference
    lateinit var loading:LoadingDialog
    var apiHit:Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingScreenBinding.inflate(layoutInflater)
        navController = findNavController()
        loading = LoadingDialog(requireContext() as Activity)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreference = CustomSharedPreference(requireContext())

        getLatitudeLongitude()

        getThemeApi()

        binding.lottieLoader.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                if (apiHit){
                    if (sharedPreference.isLogin("LOGIN")) {
                        navController.navigate(R.id.action_loadingActivity_to_dashboardActivity)
                    }
                    else{
                        navController.navigate(R.id.action_loadingActivity_to_signInFragment)
                    }
                }

            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }
        })
        Handler(Looper.getMainLooper()).postDelayed({
            binding.image.visibility = View.VISIBLE
        }, 2000)

        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.move_downward)
        binding.image.animation = anim

        binding.lottieLoader.visibility = View.VISIBLE
        binding.lottieLoader.playAnimation()
    }


    fun getLatitudeLongitude(){

        val locationManager: LocationManager = requireContext().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager

        val locationListener: LocationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
//
//                URLConstant.lat = location.latitude
//                URLConstant.long = location.longitude
//
                URLConstant.lat = 27.0
                URLConstant.long = 78.9

//                URLConstant.getEventsFinalUrl = URLConstant.getEvents + URLConstant.long + URLConstant.lat


//                Log.e("location", "Latitute: $latitude ; Longitute: $longitude ; city: $cityName ; country: $country ; address:$address")
            }

            override fun onProviderEnabled(provider: String) {

            }

            override fun onProviderDisabled(provider: String) {

            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0.0f, locationListener)
        } catch (ex:SecurityException) {
            Toast.makeText(requireContext(), "error in capturing lat/long", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getThemeApi(){
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getTheme(
                object : NetworkListener<ThemeModel> {
                    override fun successFul(t: ThemeModel) {

                        activity?.runOnUiThread {
                            apiHit = true

                            if (t.status == 1) {

                                URLConstant.themeModel = t
//                            URLConstant.check = true


                            } else {
                                StaticFields.toastClass("abcd")
                            }

                            if (!binding.lottieLoader.isAnimating) {

                                if (sharedPreference.isLogin("LOGIN")) {
                                    navController.navigate(R.id.action_loadingActivity_to_dashboardActivity)
                                } else {
                                    navController.navigate(R.id.action_loadingActivity_to_signInFragment)
                                }
                            }
                        }
                    }
                    override fun failure() {
                        activity?.runOnUiThread {
                            StaticFields.toastClass("Api syncing fail get theme")
                        }
                    }
                }
            )
        }

    }


}