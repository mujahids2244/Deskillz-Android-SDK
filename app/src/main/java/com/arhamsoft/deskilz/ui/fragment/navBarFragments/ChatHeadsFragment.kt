package com.arhamsoft.deskilz.ui.fragment.navBarFragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentChatHeadsBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.*
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.ui.adapter.RVAdapterChatHeads
import com.arhamsoft.deskilz.ui.adapter.RVAdapterRequestList
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import com.tablitsolutions.crm.activities.OnLoadMoreListener
import com.arhamsoft.deskilz.ui.adapter.RecyclerViewLoadMoreScroll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChatHeadsFragment : Fragment() {
    private lateinit var binding: FragmentChatHeadsBinding
    lateinit var loading : LoadingDialog
    lateinit var rvLoadMore: RecyclerViewLoadMoreScroll
    private lateinit var rvAdapter: RVAdapterChatHeads
    private var chatHeads:ArrayList<GetChatsHeadModelData> = ArrayList()
    lateinit var recyclerView2: RecyclerView
    lateinit var rvLoadMore2: RecyclerViewLoadMoreScroll
    private lateinit var rvAdapterAccept: RVAdapterRequestList
    private var friendReqList:ArrayList<GetFriendRequestListModelData> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentChatHeadsBinding.inflate(LayoutInflater.from(context))
        loading = LoadingDialog(requireContext() as Activity)



        binding.recycleListChatHeads.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rvAdapter = RVAdapterChatHeads(requireContext(),chatHeads,object : RVAdapterChatHeads.OnItemClick {
            override fun onUserClick(click: GetChatsHeadModelData, position: Int) {

                val bundle2 = bundleOf()
                bundle2.putInt("GLOBAL_CHAT",2)
                bundle2.putSerializable("FRIEND_ID",click.userId)
                URLConstant.chatHeadId = click.userId
                findNavController().navigate(R.id.action_chatHeadsFragment_to_chatFragment,bundle2)
            }
        })

        binding.recycleListChatHeads.adapter = rvAdapter


        binding.recycleListReq.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rvAdapterAccept = RVAdapterRequestList(requireContext(),friendReqList,object : RVAdapterRequestList.OnItemClick {


            override fun onAccept(click: GetFriendRequestListModelData, position: Int) {
                loading.startLoading()

                acceptFriendsReqApi(click.userId, true,position)
            }

            override fun onReject(click: GetFriendRequestListModelData, position: Int) {
                loading.startLoading()

                acceptFriendsReqApi(click.userId, false,position)
            }
        })




        binding.recycleListReq.adapter = rvAdapterAccept


        initScrollListener()


//                binding.progressBar.visibility = View.VISIBLE

        initScrollListener2()


        binding.addFriend.setOnClickListener {

            findNavController().navigate(R.id.action_chatHeadsFragment_to_addFriendFragment)
        }




        CoroutineScope(Dispatchers.IO).launch {
//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            u_id = user.userId
            getFriendsReqListApi(0,false)
            getChatsHead(0, false)
            withContext(Dispatchers.Main) {
                loading.startLoading()
            }
        }






        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//
//
//
//    }


    private fun getChatsHead(off: Int, isLoadMore: Boolean){

        chatHeads = ArrayList()
        val checked = ChatPost(
            20,
            off + 1,
            URLConstant.u_id!!
        )

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getChatsHeads(
                checked,
                object : NetworkListener<GetChatsHeadModel> {
                    override fun successFul(t: GetChatsHeadModel) {
                        loading.isDismiss()

                        if (t.status == 1) {

                            activity?.runOnUiThread {
                                chatHeads.addAll(t.data)

                                rvAdapter.addData(chatHeads)

                                if (isLoadMore) {
                                    rvLoadMore.setLoaded()
                                }
                            }
                        }
                        else{
                            activity?.runOnUiThread {
                                StaticFields.toastClass(t.message)
                            }
                        }
                    }

                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Apisyncing fail get chats head")
                        }
                    }
                }
            )
        }

    }

    private fun initScrollListener() {
        rvLoadMore = RecyclerViewLoadMoreScroll(binding.recycleListChatHeads.layoutManager as LinearLayoutManager)
        rvLoadMore.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMore()
            }
        })
        binding.recycleListChatHeads.addOnScrollListener(rvLoadMore)
    }

    private fun loadMore() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
//                binding.progressBar.visibility = View.VISIBLE
            }
            getChatsHead((chatHeads.size), true)
        }
    }


    private fun getFriendsReqListApi( off: Int,isLoadMore: Boolean) {

        friendReqList = ArrayList()
        val checked = ChatPost(
            20,
            off + 1,
            URLConstant.u_id!!
        )
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.getFriendReq(
                checked,
                object : NetworkListener<GetFriendRequestListModel> {
                    override fun successFul(t: GetFriendRequestListModel) {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            if (t.status == 1) {
                                friendReqList.addAll(t.data)

                                if (!(friendReqList.isNullOrEmpty()))
                                {
                                    binding.friendReqLayout.visibility = View.VISIBLE

                                    rvAdapterAccept.addData(friendReqList)
                                    if (isLoadMore) {
                                        rvLoadMore2.setLoaded()
                                    }
                                }



                            } else {
                                StaticFields.toastClass(t.message)

                            }
                        }
                    }

                    override fun failure() {
                        loading.isDismiss()
                        activity?.runOnUiThread {
                            StaticFields.toastClass("api syncing failed accept friend")
                        }

                    }
                }
            )
        }

    }

    private fun initScrollListener2() {
        rvLoadMore = RecyclerViewLoadMoreScroll(binding.recycleListReq.layoutManager as LinearLayoutManager)
        rvLoadMore.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMore2()
            }
        })
        binding.recycleListReq.addOnScrollListener(rvLoadMore)
    }

    private fun loadMore2() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
//                binding.progressBar.visibility = View.VISIBLE
            }
            getFriendsReqListApi((friendReqList.size), true)
        }
    }

    fun acceptFriendsReqApi(playerId:String, accepted:Boolean, position:Int){
//
//        val checked = chatPost(
//            10,
//            off + 1,
//            "1234564789"
//        )

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.acceptFriendReq(
                URLConstant.u_id!!,
                playerId,
                accepted,
                object : NetworkListener<AcceptFriendModel> {
                    override fun successFul(t: AcceptFriendModel) {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            if (t.status == 1) {

                                StaticFields.toastClass(t.message)
                                friendReqList.removeAt(position)
                                rvAdapterAccept.notifyItemRemoved(position)
                                rvAdapterAccept.notifyDataSetChanged()
                                getChatsHead(0, false)



                            } else {
                                StaticFields.toastClass(t.message)

                            }
                        }
                    }

                    override fun failure() {
                        loading.isDismiss()
                        activity?.runOnUiThread {

                            StaticFields.toastClass("Apisyncing fail accept friend")
                        }
                    }
                }
            )
        }

    }



}