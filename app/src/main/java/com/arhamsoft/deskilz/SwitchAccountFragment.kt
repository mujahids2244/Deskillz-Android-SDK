package com.arhamsoft.deskilz

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alphaCareInc.app.room.User
import com.alphaCareInc.app.room.UserDatabase
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.databinding.FragmentSwitchAccountBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.LoginModel
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.ui.adapter.AdapterSwitchAcc
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SwitchAccountFragment : Fragment() {

    lateinit var binding:FragmentSwitchAccountBinding
   var usersList: ArrayList<User> = ArrayList()
    private lateinit var adapterSwitch: AdapterSwitchAcc
    lateinit var loading:LoadingDialog
    lateinit var sharedPreference: CustomSharedPreference
    var fcmToken:String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSwitchAccountBinding.inflate(layoutInflater)
        loading = LoadingDialog(requireContext() as Activity)
        sharedPreference = CustomSharedPreference(requireContext())


        binding.gotoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_switchAccountFragment_to_signInFragment)
        }
        if (sharedPreference.returnValue("TOKEN") != null){
            fcmToken = sharedPreference.returnValue("TOKEN")
        }

        CoroutineScope(Dispatchers.IO).launch {
             usersList  = UserDatabase.getDatabase(requireContext()).userDao().getUserList() as ArrayList<User>
            adapterSwitch.setData(usersList)

        }



        adapterSwitch = AdapterSwitchAcc(object : AdapterSwitchAcc.OnItemClickListenerHandler {

            override fun onItemClicked(click: User, position: Int) {

                if (click.userId == URLConstant.u_id){
                    StaticFields.toastClass("Current User Already Login")
                }
                else{
                    loading.startLoading()
                    switchAccApiCalling(click,URLConstant.u_id!!,fcmToken!!)
//
                }


//
           }

        })

        binding.recycleListRanking.adapter = adapterSwitch




        return binding.root
    }


    fun switchAccApiCalling(user: User,p_id:String,token:String){

                CoroutineScope(Dispatchers.IO).launch {

                    NetworkRepo.switchAcc(user.userId!!,p_id,token,
                        object : NetworkListener<LoginModel> {
                            override fun successFul(t: LoginModel) {
                                loading.isDismiss()

                                activity?.runOnUiThread {
                                    if (t.status == 1) {

                                        StaticFields.toastClass(t.message)

                                        URLConstant.u_id = t.data.userID
                                        sharedPreference.saveCurrentLoginID("user",user.id)
//

                                        sharedPreference.saveValue("USERIMG", t.data.userImage)
                                        sharedPreference.saveValue("USERNAME", t.data.userName)

                                        sharedPreference.saveLogin("LOGIN", true)

                                        findNavController().navigate(R.id.action_switchAccountFragment_to_dashboardActivity)
//
                                    } else {
                                        StaticFields.toastClass(t.message)


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




