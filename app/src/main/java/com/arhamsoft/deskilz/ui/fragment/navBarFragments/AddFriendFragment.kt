package com.arhamsoft.deskilz.ui.fragment.navBarFragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.deskilz.databinding.FragmentAddFriendBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.*
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.ui.adapter.RVAdapterGetAllUsers
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import com.tablitsolutions.crm.activities.OnLoadMoreListener
import com.arhamsoft.deskilz.ui.adapter.RecyclerViewLoadMoreScroll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddFriendFragment : Fragment() {
    private lateinit var binding: FragmentAddFriendBinding
    lateinit var loading : LoadingDialog
    lateinit var recyclerView: RecyclerView
    lateinit var rvLoadMore: RecyclerViewLoadMoreScroll
    private lateinit var rvAdapter: RVAdapterGetAllUsers
    private var allUsersList:ArrayList<GetAllUsersModelData> = ArrayList()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddFriendBinding.inflate(LayoutInflater.from(context))
        loading = LoadingDialog(requireContext() as Activity)


        loading.startLoading()
        showAddFriendsListApi(0,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.acceptReq.setOnClickListener {
//
//            findNavController().navigate(R.id.action_addFriendFragment_to_acceptFriendReqFragment)
//        }


            binding.backToAccount.setOnClickListener {
                findNavController().popBackStack()
            }






            binding.recycleListChatHeads.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        rvAdapter = RVAdapterGetAllUsers(requireContext(),allUsersList,object : RVAdapterGetAllUsers.OnItemClick {
            override fun onUserClick(click: GetAllUsersModelData, position: Int) {

//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            u_id = user.userId
                loading.startLoading()
                addFriendsApi(click.userId)


            }
        })


        binding.recycleListChatHeads.adapter = rvAdapter

    }

    private fun showAddFriendsListApi( off: Int,isLoadMore: Boolean) {

//        allUsersList = ArrayList()
        val checked = ChatPost(
            30,
            off + 1,
            URLConstant.u_id!!
        )
        CoroutineScope(Dispatchers.IO).launch {
         NetworkRepo.getAllUsers(
             checked,
             object : NetworkListener<GetAllUsersModel> {
                 override fun successFul(t: GetAllUsersModel) {

                     loading.isDismiss()
                     activity?.runOnUiThread {

                     if (t.status == 1) {
                         allUsersList.addAll(t.data)

                         rvAdapter.addData(allUsersList)

                         initScrollListener()
                         if (isLoadMore) {
                             rvLoadMore.setLoaded()
                         }
                         binding.progressBar.visibility = View.GONE

                     }
                     else{
                         StaticFields.toastClass(t.message)

                     }

                     }
                 }

                 override fun failure() {
                     loading.isDismiss()
                     activity?.runOnUiThread {
                         StaticFields.toastClass("syncing failed add friend api")
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
                binding.progressBar.visibility = View.VISIBLE
            }
            showAddFriendsListApi((allUsersList.size), true)
        }
    }

    fun addFriendsApi(playerId:String){
//
//        val checked = chatPost(
//            10,
//            off + 1,
//            "1234564789"
//        )

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.addFriends(
                URLConstant.u_id!!,
                playerId,
                object : NetworkListener<AddFriendModel> {
                    override fun successFul(t: AddFriendModel) {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            if (t.status == 1) {

                                StaticFields.toastClass(t.message)

                            } else {
                                StaticFields.toastClass(t.message)

                            }
                        }
                    }

                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Apisyncing fail add friend")
                        }
                    }
                }
            )
        }

    }



}