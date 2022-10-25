package com.arhamsoft.deskilz.ui.fragment.navBarFragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentHomeScreenBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.*
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.ui.adapter.AdapterHomeScreen
import com.arhamsoft.deskilz.ui.adapter.RVAdapterPlayersWaiting
import com.arhamsoft.deskilz.utils.InternetConLiveData
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import com.tablitsolutions.crm.activities.OnLoadMoreListener
import com.arhamsoft.deskilz.ui.adapter.RecyclerViewLoadMoreScroll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var adapterHomeScreen: AdapterHomeScreen
    private lateinit var navController: NavController
    lateinit var loading:LoadingDialog
    lateinit var recyclerView: RecyclerView
    lateinit var rvLoadMore: RecyclerViewLoadMoreScroll
    var completedList:ArrayList<GetMatchesRecordData> = ArrayList()
    private lateinit var connection: InternetConLiveData
    private lateinit var rvAdapter: RVAdapterPlayersWaiting

    var requestList:ArrayList<PlayerWaitingModelData> = ArrayList()
    var u_id:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        navController = findNavController()
        loading = LoadingDialog(requireContext() as Activity)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



//        CoroutineScope(Dispatchers.IO).launch {
//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            if (user != null) {
//                u_id = user.userId
//            }
//        }
//        checkNetworkConnection()

        if (!(StaticFields.isNetworkConnected(requireContext()))){
            StaticFields.toastClass("Check your network connection")
        }
        else{
            loading.startLoading()
//        URLConstant.check = false
//        getGameCustomData()
            getWaitingList()
            getMatchesRecord(0,false)
        }


        initClicks()

//        initScrollListener()

        adapterHomeScreen = AdapterHomeScreen(object : AdapterHomeScreen.OnItemClickListenerHandler {

            override fun onItemClicked(click: GetMatchesRecordData, position: Int) {



                val bundle = bundleOf()
                bundle.putSerializable("MATCHRECORDOBJECT",click)
//                bundle.putString("MATCHID",click.matchId)
//                bundle.putLong("TICKETS",click.cash)
//                bundle.putLong("XPOPPONENT",click.xpValueOther)
//                bundle.putLong("XPUSER",click.xpValueUser)


                navController.navigate(R.id.action_dashboardActivity_to_gameResultFragment, bundle)
            }

        })
        binding.recycleListHome.adapter = adapterHomeScreen


        rvAdapter = RVAdapterPlayersWaiting(object : RVAdapterPlayersWaiting.OnItemClickListenerHandler {
            override fun onItemClicked(click: Any, position: Int) {

//                findNavController().navigate(R.id.action_pendingRequestFragment_to_findCompetitiveFragment)

            }
        })

        binding.recycleListRequest.adapter = rvAdapter



    }
    private fun checkNetworkConnection(){

        connection = InternetConLiveData(requireContext())

        connection.observe(viewLifecycleOwner) { isConnected ->

            if (isConnected){
                binding.noint.noInternet.visibility = View.GONE

            }
            else {
                binding.noint.noInternet.visibility = View.VISIBLE

            }
        }

    }


    private fun initClicks() {
        binding.deskillzLevelLayout.setOnClickListener {
            navController.navigate(R.id.action_dashboardActivity_to_levelScreenFragment)
        }

        binding.battleFieldLayout.setOnClickListener {
            navController.navigate(R.id.action_dashboardActivity_to_levelScreenFragment)
        }

        binding.bronzeTiersLayout.setOnClickListener {
            navController.navigate(R.id.action_dashboardActivity_to_rewardFragment)
        }

    }



    private fun getMatchesRecord(off: Int, isLoadMore: Boolean){


        val checked = ChatPost(
            5,
            off + 1,
            URLConstant.u_id!!
        )

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getMatchesRecord(
                checked,
                object : NetworkListener<GetMatchesRecord> {
                    override fun successFul(t: GetMatchesRecord) {
                        loading.isDismiss()
                        if (t.status == 1) {





                            activity?.runOnUiThread {

                            if (t.data.isNotEmpty()) {


                                    completedList.addAll(t.data)

                                completedList.reverse()
                                    adapterHomeScreen.setData(completedList)

//                                if (isLoadMore) {
//                                    rvLoadMore.setLoaded()
//                                }

//                                binding.progressBar.visibility = View.GONE
                            }
                            else{

                                adapterHomeScreen.setData(completedList)
                            }


                            }
//                                URLConstant.check = true
                        }

                    }
                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Api syncing fail get events")
                        }
                    }
                }
            )
        }

    }

    private fun initScrollListener() {
        rvLoadMore = RecyclerViewLoadMoreScroll(binding.recycleListHome.layoutManager as LinearLayoutManager)
        rvLoadMore.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMore()
            }
        })
        binding.recycleListHome.addOnScrollListener(rvLoadMore)
    }

    private fun loadMore() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
//                binding.progressBar.visibility = View.VISIBLE
            }
            getMatchesRecord((completedList.size), true)
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
                                    binding.ongoingLayout.visibility = View.VISIBLE

                                    requestList.addAll(t.data)

                                    rvAdapter.setData(requestList)
                                }
                                else{
                                    binding.ongoingLayout.visibility = View.GONE
//                                    rvAdapter.setData(requestList)
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