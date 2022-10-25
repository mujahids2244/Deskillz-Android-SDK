package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentRewardScreenBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.*
import com.arhamsoft.deskilz.ui.adapter.RVAdapterReward
import com.arhamsoft.deskilz.ui.adapter.RVAdapterRewardAllCategory
import com.arhamsoft.deskilz.ui.adapter.RVAdapterRewardAllSubCategory
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RewardFragment : Fragment(){

    private lateinit var binding: FragmentRewardScreenBinding
    private lateinit var navController: NavController
    lateinit var loading : LoadingDialog
//    lateinit var recyclerView: RecyclerView
    private var marketList:ArrayList<ProductInfoModel> = ArrayList()
    private var marketLoadMoreList:ArrayList<GetMarketLoadMoreModelData> = ArrayList()
    private var m_id:String? =null
//    lateinit var rvLoadMore: RecyclerViewLoadMoreScroll
    private lateinit var rvAdapter: RVAdapterReward
    private lateinit var rvAdapterAllCategory: RVAdapterRewardAllCategory
    private lateinit var rvAdapterAllSubCategory: RVAdapterRewardAllSubCategory
    var apiHit:Boolean= false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRewardScreenBinding.inflate(layoutInflater)
        navController = findNavController()

        loading = LoadingDialog(requireContext() as Activity)
        binding.goToDetailScreen.setOnClickListener {
            navController.navigate(R.id.action_rewardFragment_to_deskillzRewardInstructionFragment)
        }

        binding.backBtn.setOnClickListener {
            navController.popBackStack()
            //onBackPressed()
        }

        if (!(StaticFields.isNetworkConnected(requireContext()))){
            StaticFields.toastClass("Check your network connection")
        }
        else{
            if (!(apiHit)) {
                loading.startLoading()
                callMarketApi(10)
                }
        }


//        CoroutineScope(Dispatchers.IO).launch {
////            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
////            u_id = user.userId
//            if (!(apiHit)) {
//                callMarketApi(10)
//                withContext(Dispatchers.Main) {
//                    loading.startLoading()
//                }
//            }
//        }

        binding.recycleListReward.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        rvAdapter = RVAdapterReward(requireContext(),marketList,object : RVAdapterReward.OnItemClick {
            override fun onClick(click: ProductInfoModel, position: Int) {

            }
        })

        binding.recycleListReward.adapter = rvAdapter

        ////
        binding.recycleListallMarket.layoutManager = LinearLayoutManager(requireContext())

        rvAdapterAllCategory = RVAdapterRewardAllCategory(requireContext(),
            object: RVAdapterRewardAllCategory.OnItemClick {
                override fun onClick(click: GetMarketLoadMoreModelData, position: Int) {
//                initScrollListener()
                    CoroutineScope(Dispatchers.IO).launch {
//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            u_id = user.userId
                        m_id = click.marketId
                        callMarketLoadMoreApi((marketLoadMoreList.size), true, position)
                        withContext(Dispatchers.Main) {
                            loading.startLoading()
                        }
                    }

                }
            })

        binding.recycleListallMarket.adapter = rvAdapterAllCategory

        return binding.root
    }


    private fun callMarketApi(limit: Int){

//        val checked = MarketLoadMorePost(
//            10,
//            off + 1,
//        )

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getMarket(
                limit,
                object : NetworkListener<GetMarketLoadMoreModel> {
                    override fun successFul(t: GetMarketLoadMoreModel) {
                        apiHit = true
                        loading.isDismiss()
                        if (t.status == 1) {

                            for (i in 0 until t.data.size) {
                                marketList.addAll(t.data[i].productInfo)
                                marketLoadMoreList.addAll(t.data)
                            }

                            activity?.runOnUiThread {
                                rvAdapter.addData(marketList)
//                                Toast.makeText(requireContext(), ""+marketLoadMoreList.size, Toast.LENGTH_SHORT).show()
                                rvAdapterAllCategory.addData(marketLoadMoreList)
                            }
                        }
                    }

                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Api syncing fail get market")
                        }
                    }
                }
            )
        }

    }

    fun callMarketLoadMoreApi(off: Int, isLoadMore: Boolean, position:Int){

        val checked = MarketLoadMorePost(
            10,
            off + 1,
            m_id!!
        )

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getMarketLoadMore(
                checked,
                object : NetworkListener<GetMarketLoadMoreModel> {
                    override fun successFul(t: GetMarketLoadMoreModel) {
                        loading.isDismiss()
                        if (t.status == 1) {

//                            for (i in 0 until t.data.size) {
//                                marketLoadMoreList.addAll(listOf(t.data[i]))
//                            }
                            activity?.runOnUiThread {
                                rvAdapterAllCategory.loadMoreDataInSub(t.data[0], position)
//                               rvAdapterAllSubCategory.addData(marketLoadMoreList)

                            }
//                            if (isLoadMore) {
//                                rvLoadMore.setLoaded()
//                            }

                        }
                    }

                    override fun failure() {
                        loading.isDismiss()

                        StaticFields.toastClass("Api syncing fail get market LoadMore")

                    }
                }
            )
        }

    }


//    private fun initScrollListener() {
//        rvLoadMore = RecyclerViewLoadMoreScroll(binding.recycleListallMarket.layoutManager as LinearLayoutManager)
//        rvLoadMore.setOnLoadMoreListener(object : OnLoadMoreListener {
//            override fun onLoadMore() {
//                loadMore()
//            }
//        })
//        binding.recycleListallMarket.addOnScrollListener(rvLoadMore)
//    }

    private fun loadMore() {
//        CoroutineScope(Dispatchers.IO).launch {
//            withContext(Dispatchers.Main) {
////                binding.progressBar.visibility = View.VISIBLE
//            }
//            callMarketLoadMoreApi((marketLoadMoreList.size), true)
//        }
    }

}