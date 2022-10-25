package com.arhamsoft.deskilz.ui.fragment.navBarFragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.deskilz.databinding.FragmentLeagueScreenBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.*
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.ui.adapter.RVAdapterRanking
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import com.tablitsolutions.crm.activities.OnLoadMoreListener
import com.arhamsoft.deskilz.ui.adapter.RecyclerViewLoadMoreScroll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LeagueScreenFragment : Fragment() {

    private lateinit var binding: FragmentLeagueScreenBinding
    lateinit var loading:LoadingDialog
    lateinit var recyclerView: RecyclerView
    lateinit var rvLoadMore: RecyclerViewLoadMoreScroll
    private lateinit var rvAdapter: RVAdapterRanking

    var rankingList:ArrayList<PlayerRankingModelData> = ArrayList()
    var u_id:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeagueScreenBinding.inflate(inflater)
        // Inflate the layout for this fragment

        loading = LoadingDialog(requireContext() as Activity)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initClicks()

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
//        URLConstant.check = false
            getRankings(0,false)


        }

//        initScrollListener()


        rvAdapter = RVAdapterRanking(object : RVAdapterRanking.OnItemClickListenerHandler {
            override fun onItemClicked(click: PlayerRankingModelData, position: Int) {
            }
        })

        binding.recycleListRanking.adapter = rvAdapter


    }

//    private fun initClicks() {
//
//    }



    private fun getRankings(off: Int, isLoadMore: Boolean){


        val checked = ChatPost(
            10,
            off + 1,
            URLConstant.u_id!!
        )

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getPlayerRanking(
                checked,
                object : NetworkListener<PlayerRankingModel> {
                    override fun successFul(t: PlayerRankingModel) {
                        loading.isDismiss()
                        if (t.status == 1) {





                            activity?.runOnUiThread {

                                if (t.data.isNotEmpty()) {


                                    rankingList.addAll(t.data)

                                    rankingList.sortBy {
                                        it.SDK_Rank
                                    }
                                    rvAdapter.setData2(rankingList,2)
                                }
                                else{

                                    rvAdapter.setData2(rankingList,2)
                                }

                                if (isLoadMore) {
                                    rvLoadMore.setLoaded()
                                }
                                binding.progressBar.visibility = View.GONE

//                                URLConstant.check = true

                            }

                        }

                    }
                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Api syncing fail get notifications")
                        }
                    }
                }
            )
        }

    }

    private fun initScrollListener() {
        rvLoadMore = RecyclerViewLoadMoreScroll(binding.recycleListRanking.layoutManager as LinearLayoutManager)
        rvLoadMore.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMore()
            }
        })
        binding.recycleListRanking.addOnScrollListener(rvLoadMore)
    }

    private fun loadMore() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.VISIBLE
            }
            getRankings((rankingList.size), true)
        }
    }


}