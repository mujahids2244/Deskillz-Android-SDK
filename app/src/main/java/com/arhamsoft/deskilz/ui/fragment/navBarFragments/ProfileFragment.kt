package com.arhamsoft.deskilz.ui.fragment.navBarFragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentProfileBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.UserDetailedInfoModel
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.ui.adapter.AdapterTrophies
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var loading: LoadingDialog
    lateinit var sharedPreference: CustomSharedPreference

    lateinit var rvAdapter: AdapterTrophies
    var trophyList: ArrayList<String> = ArrayList()

    private var u_id: String? = " "

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(LayoutInflater.from(context))
        loading = LoadingDialog(requireContext() as Activity)
        sharedPreference = CustomSharedPreference(requireContext())

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if(sharedPreference.returnValue("USERIMG") != null) {
//
//            val img= StringToBitMap( sharedPreference.returnValue("USERIMG"))
////            bitmap = byteArray?.let { Imgconvertors.toBitmap(it) }!!
//            binding.userImg.load(img){
//                placeholder(R.drawable.ic_baseline_person_24)
//                error(R.drawable.ic_baseline_person_24)
//            }
//        }


        binding.recycleListTrophies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        rvAdapter = AdapterTrophies(object : AdapterTrophies.OnItemClickListenerHandler {
            override fun onItemClicked(click: String, position: Int) {

            }
        })

        binding.recycleListTrophies.adapter = rvAdapter

    trophyList = ArrayList()
    for (i in 1 until 10){
        trophyList.add("trophy")
    }
        rvAdapter.setData(trophyList)

        binding.backtoDashboard.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.userImg.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment)
        }

        CoroutineScope(Dispatchers.IO).launch {
//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            if (user != null) {
//                u_id = user.userId
//            }
            getUserDetailedInfo()

        }

    }


//    private fun StringToBitMap(encodedString: String?): Bitmap? {
//        return try {
//            val encodeByte =
//                Base64.decode(encodedString, Base64.DEFAULT)
//            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
//        } catch (e: Exception) {
//            e.message
//            null
//        }
//    }


    private fun getUserDetailedInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                loading.startLoading()
            }
            NetworkRepo.getUserDetailedInfo(
                URLConstant.u_id!!,
                object : NetworkListener<UserDetailedInfoModel> {
                    override fun successFul(t: UserDetailedInfoModel) {
                        loading.isDismiss()
                        if (t.status == 1) {

                            activity?.runOnUiThread {

                                sharedPreference.saveValue("USERIMG", t.data.userData.userImage)
                                sharedPreference.saveValue("USERNAME", t.data.userData.userName)
                                sharedPreference.saveValue("SHOUTOUT", t.data.userData.userShoutOut)

                                binding.userImg.load(t.data.userData.userImage) {
                                    placeholder(R.drawable.ic_baseline_person_24)
                                    error(R.drawable.ic_baseline_person_24)
                                }
                                binding.Flag.load(t.data.userData.userCountryFlag)
                                binding.userName.text = t.data.userData.userName
                                binding.progressXp.text = t.data.deskillzLevel.toString()
                                binding.progressXp2.text = "1"
                                binding.progressXp3.text = t.data.currentGameRank.toString()
                                binding.coinNo.text = t.data.progressXp.toString()
//                                binding.ticketsNo.text = "0"
                                binding.dollar.text = "0.0"

                                binding.winStreak.text = t.data.winStreak.toString()
                                binding.matchesWon.text = t.data.userWin.toString()
                                binding.shoutout.text = t.data.userData.userShoutOut
                                binding.win.text = t.data.userWin.toString()
                                binding.lose.text = t.data.userLoose.toString()


                            }

                        }


                    }


                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Api syncing fail user detail info ")
                        }
                    }
                }
            )
        }

    }
}