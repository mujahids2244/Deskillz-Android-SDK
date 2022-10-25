package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentPendingRequestBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.PlayerWaitingModel
import com.arhamsoft.deskilz.networking.networkModels.PlayerWaitingModelData
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.ui.adapter.RVAdapterPlayersWaiting
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import com.arhamsoft.deskilz.ui.adapter.RecyclerViewLoadMoreScroll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PendingRequestFragment : Fragment() {

    lateinit var binding: FragmentPendingRequestBinding
    lateinit var loading:LoadingDialog
    lateinit var recyclerView: RecyclerView
    lateinit var rvLoadMore: RecyclerViewLoadMoreScroll
    private lateinit var rvAdapter: RVAdapterPlayersWaiting

    var requestList:ArrayList<PlayerWaitingModelData> = ArrayList()
    var u_id:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPendingRequestBinding.inflate(LayoutInflater.from(context))
        loading = LoadingDialog(requireContext() as Activity)


        rvAdapter = RVAdapterPlayersWaiting(object : RVAdapterPlayersWaiting.OnItemClickListenerHandler {
            override fun onItemClicked(click: Any, position: Int) {

                findNavController().navigate(R.id.action_pendingRequestFragment_to_findCompetitiveFragment)

            }
        })

        binding.recycleListRequest.adapter = rvAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


//        CoroutineScope(Dispatchers.IO).launch {
//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            if (user != null) {
//                u_id = user.userId
//            }
//        }
        if (!(StaticFields.isNetworkConnected(requireContext()))){
            StaticFields.toastClass("Check your network connection")
        }
        else{
            loading.startLoading()
            getWaitingList()

        }



    }


    private fun getWaitingList(){

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.playerWaiting(
                URLConstant.u_id!!,
                object : NetworkListener<PlayerWaitingModel> {
                    override fun successFul(t: PlayerWaitingModel) {
                        loading.isDismiss()
                        if (t.status == 1) {
                            activity?.runOnUiThread {

                                if (t.data.isNotEmpty()) {
                                    requestList.addAll(t.data)

                                    rvAdapter.setData(requestList)
                                }
                                else{

                                    rvAdapter.setData(requestList)
                                }
                            }
                        }
                    }
                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Api syncing fail pending request")
                        }
                    }
                }
            )
        }


    }


}