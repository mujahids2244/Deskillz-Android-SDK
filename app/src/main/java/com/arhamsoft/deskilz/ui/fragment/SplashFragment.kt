package com.arhamsoft.deskilz.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentSplashBinding
import com.arhamsoft.deskilz.utils.CustomSharedPreference

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var navController: NavController
    lateinit var sharedPreference: CustomSharedPreference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        navController = findNavController()
        sharedPreference = CustomSharedPreference(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                if (activity?.intent?.extras != null) {
                    val bundle2 = bundleOf()

                    val map = HashMap<String, String>()

                    for (key in activity?.intent?.extras!!.keySet()) {
                        activity?.intent?.extras!!.getString(key)?.let { map.put(key, it) }
                    }

                    if (map["notificationType"]?.toInt() == 4){
                navController.navigate(R.id.startSDKFragment)

                    }
                    else if (map["notificationType"]?.toInt() == 6){
                        navController.navigate(R.id.startSDKFragment)

                    }
                    else if (map["notificationType"]?.toInt() == 5){
                        navController.navigate(R.id.action_splashFragment_to_chatHeadsFragment)
                    }
                    else if (map["notificationType"]?.toInt() == 3){
                        sharedPreference.saveLogin("LOGIN", false)
                navController.navigate(R.id.startSDKFragment)
                    }
                    else if (map["notificationType"]?.toInt() == 0){
                        bundle2.putInt("GLOBAL_CHAT", 1)

                navController.navigate(R.id.action_splashFragment_to_chatFragment,bundle2)

                    }
                    else if (map["notificationType"]?.toInt() == 1){
                        bundle2.putInt("GLOBAL_CHAT", 2)
                        bundle2.putSerializable("FRIEND_ID", map["fromId"])
                navController.navigate(R.id.action_splashFragment_to_chatFragment,bundle2)

                    }
                    else{
                        navController.navigate(R.id.action_splashFragment_to_startSDKFragment)

                    }
                }else {


                    navController.navigate(R.id.action_splashFragment_to_startSDKFragment)
                }
                     }, 1000)
    }
}