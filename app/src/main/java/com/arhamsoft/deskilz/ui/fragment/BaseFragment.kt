package com.arhamsoft.deskilz.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.arhamsoft.deskilz.utils.InternetConLiveData

open class BaseFragment: Fragment() {

    private lateinit var connection:InternetConLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        checkNetworkConnection()
    }
//      fun checkNetworkConnection(){
//
//         connection = InternetConLiveData(requireContext())
//
//         connection.observe(viewLifecycleOwner) { isConnected ->
//
//             if (isConnected) {
//                 binding.noint.noInternet.visibility = View.GONE
//
//             }
//             else {
//                 binding.noint.noInternet.visibility = View.VISIBLE
//
//             }
//         }
//
//     }


 }