package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.alphaCareInc.app.room.UserDatabase
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentDashboardBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.ForgotModel
import com.arhamsoft.deskilz.networking.networkModels.GetPlayerAccount
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.ui.fragment.navBarFragments.CartScreenFragment
import com.arhamsoft.deskilz.ui.fragment.navBarFragments.HomeScreenFragment
import com.arhamsoft.deskilz.ui.fragment.navBarFragments.LeagueScreenFragment
import com.arhamsoft.deskilz.ui.fragment.navBarFragments.PlayScreenFragment
import com.arhamsoft.deskilz.utils.InternetConLiveData
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.*
import java.lang.Runnable

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var navController: NavController
    lateinit var loading: LoadingDialog
    lateinit var sharedPreference: CustomSharedPreference
    private lateinit var connection: InternetConLiveData
     var id:Int?=0



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        navController = findNavController()
        sharedPreference = CustomSharedPreference(requireContext())
        loading = LoadingDialog(requireContext() as Activity)
        checkNetworkConnection()

//        val gson = Gson()
         id = sharedPreference.returnCurrentLoginID("user")
//        obj3 = gson.fromJson(json, User::class.java)

        runBlocking {

            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser(id!!)
            if (user != null) {
                URLConstant.u_id = user.userId
                Log.e("userid", "onCreateView: ${URLConstant.u_id}", )
            } else {
                Log.e("useridError", "onCreateView: ${URLConstant.u_id}", )
            }
        }



        if (sharedPreference.returnValue("USERIMG") != null
            && sharedPreference.returnValue("USERNAME") != null) {
            binding.menu.userImg.load(sharedPreference.returnValue("USERIMG")) {
                placeholder(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
            }
            //underline text
            val mSpannableString = SpannableString(sharedPreference.returnValue("USERNAME") ?: "Name")
            mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
            binding.menu.userName.text = mSpannableString

        }

        return binding.root
    }


    private fun checkNetworkConnection(){

        connection = InternetConLiveData(requireContext())

        connection.observe(viewLifecycleOwner) { isConnected ->

            if (isConnected){
                binding.noint.noInternet.visibility = View.GONE
                loading.startLoading()
                playerPointsStatusApi()
            }
            else {
                binding.noint.noInternet.visibility = View.VISIBLE

            }
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




            val isCart = arguments?.getBoolean("FromOutOfFund", false)

            initMenu()
            initClicks()

            if (isCart == true)
                replaceFragment(CartScreenFragment())
            else replaceFragment(HomeScreenFragment())


            binding.bottomNavigation.setOnItemSelectedListener { item ->
                when (item) {
                    0 -> {
//                        if (URLConstant.check){
                            replaceFragment(HomeScreenFragment())
//                        }
//                        else{
//                            Toast.makeText(requireContext(),"Please Wait While data is being loaded",Toast.LENGTH_SHORT).show()
//                        }
                    }
                    1 -> {
//                        if (URLConstant.check){
                            replaceFragment(PlayScreenFragment())
//                        }
//                        else{
//                            Toast.makeText(requireContext(),"Please Wait While data is being loaded",Toast.LENGTH_SHORT).show()
//                        }
                    }
//                    2 -> {
//                        replaceFragment(CartScreenFragment())
//                    }
                    2 -> {
//                        if (URLConstant.check){
                            replaceFragment(LeagueScreenFragment())
//                        }
//                        else{
//                            Toast.makeText(requireContext(),"Please Wait While data is being loaded",Toast.LENGTH_SHORT).show()
//                        }
                    }
                }
            }
//        binding.bottomNavigation.onItemSelected?.invoke(R.id.playscreen)
//        binding.bottomNavigation.menu().findItem(R.id.playscreen).setChecked(true)
//        binding.bottomNavigation.onItemSelected?.let { it(1) }



    }



    private fun initClicks() {
//        binding.ticketLayout.setOnClickListener {
//            navController.navigate(R.id.action_dashboardActivity_to_rewardFragment)
//        }
        binding.gotoRedeemScreen.setOnClickListener {
            navController.navigate(R.id.action_dashboardActivity_to_redeemPointsFragment)
        }


        binding.msgLayout.setOnClickListener {
            val bundle2 = bundleOf()
            bundle2.putInt("GLOBAL_CHAT",1)
            navController.navigate(R.id.action_dashboardActivity_to_chatFragment,bundle2)
        }
    }

    private fun initMenu() {
        binding.menuBtn.setOnClickListener {
            binding.menu.menuLayout.visibility = View.VISIBLE
            //startActivity(Intent(this, MenuActivity::class.java))
        }

        binding.menu.userAccount.setOnClickListener {
            binding.menu.menuLayout.visibility = View.GONE

            navController.navigate(R.id.action_dashboardActivity_to_profileFragment)
        }

        binding.menu.extraView.setOnClickListener {
            binding.menu.menuLayout.visibility = View.GONE
        }

        binding.menu.deskillzNewLayout.setOnClickListener {
            binding.menu.menuLayout.visibility = View.GONE

            navController.navigate(R.id.action_dashboardActivity_to_deskillzNewFragment)

        }

        binding.menu.gotoNotifications.setOnClickListener {
            binding.menu.menuLayout.visibility = View.GONE

            navController.navigate(R.id.action_dashboardActivity_to_notificationsFragment)
        }
        binding.menu.accountSettings.setOnClickListener {
            binding.menu.menuLayout.visibility = View.GONE

            navController.navigate(R.id.action_dashboardActivity_to_accountSettingFragment)
        }
        binding.menu.emptyClick.setOnClickListener {  }
        binding.menu.gotoChat.setOnClickListener {
            binding.menu.menuLayout.visibility = View.GONE

            val bundle2 = bundleOf()
            bundle2.putInt("GLOBAL_CHAT",1)
            navController.navigate(R.id.action_dashboardActivity_to_chatFragment,bundle2)
        }
        binding.menu.layoutLogout.setOnClickListener {
            binding.menu.menuLayout.visibility = View.GONE

            if (StaticFields.isNetworkConnected(requireContext())){
//            loading.startLoading()
            logoutUser()
        }
        else{
          StaticFields.toastClass("Check your network connection")

        }

        }

    }

    private fun replaceFragment(fragment: Fragment) {

//        supportFragmentManager.beginTransaction().remove(this).commit()
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragContainer, fragment)
            ?.commit()
    }


     fun logoutUser(){
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.logout(
                URLConstant.u_id!!,
            object : NetworkListener<ForgotModel> {
                override fun successFul(t: ForgotModel) {
//                    loading.isDismiss()
                        activity?.runOnUiThread {
                            if (t.status == 1) {

                              StaticFields.toastClass(t.message)
                                sharedPreference.saveLogin("LOGIN", false)
                                clearCurrentUserDB()
                                URLConstant.u_id = "0"
                                findNavController().navigate(R.id.action_dashboardActivity_to_signInFragment)
                            }
                            else{
                                StaticFields.toastClass(t.message)

                            }
                    }
                }

                override fun failure() {
//                    loading.isDismiss()

                    activity?.runOnUiThread {
                        StaticFields.toastClass("api failure logout")
                    }
                }
            })
        }
    }

    private fun playerPointsStatusApi(){
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getPlayerAccount(
                URLConstant.u_id!!,
                object : NetworkListener<GetPlayerAccount> {
                    override fun successFul(t: GetPlayerAccount) {
                        loading.isDismiss()

                        if (t.status == 1 ) {

                            activity?.runOnUiThread {
//                                binding.ticketsNo.text = t.data.userTickets.toString()
                                binding.coinNo.text = t.data.userDeskillzCurrency.toString()
                                URLConstant.points = t.data.userDeskillzCurrency.toString()
                                binding.dollar.text = t.data.userDollers.toString()
                            }
                        }
                    }

                    override fun failure() {
                        loading.isDismiss()
                        activity?.runOnUiThread {

                            StaticFields.toastClass("Apisyncing fail player account")
                        }
                    }
                }
            )
        }

    }

    private fun clearCurrentUserDB() {
        val th = Thread(Runnable {
            //clear data from db
            UserDatabase.getDatabase(requireContext()).userDao().deleteUser(id!!)
        })
        th.start()
        th.join()
    }

//    private fun responseApiLiveData() {
//
//        NetworkRepo.forgotsuccessLiveData.observe(viewLifecycleOwner) {
//            if(it.status){
//                loading.isDismiss()
//                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.action_forgotFragment_to_signInFragment)
//            }
//            else{
//                loading.isDismiss()
//                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        NetworkRepo.errorLiveData.observe(viewLifecycleOwner){
//
//            if (it.status == 1) {
//                loading.isDismiss()
//
//                Toast.makeText(
//                    requireContext(),
//                    "api syncing failed forgot ${it.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//                it.status = 0
//            }
//        }
//    }
//    override fun onResume() {
//    super.onResume()
//
//
//}

}