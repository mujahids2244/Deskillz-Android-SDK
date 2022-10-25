package com.arhamsoft.deskilz.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import android.util.Base64
import android.view.View
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.CardGetAllUserListsBinding
import com.arhamsoft.deskilz.databinding.CardGetRequestListBinding
import com.arhamsoft.deskilz.databinding.CardViewChatBinding
import com.arhamsoft.deskilz.networking.networkModels.*

import java.util.Base64.getDecoder
import kotlin.collections.ArrayList


class RVAdapterRequestList(
    var context: Context,
    private var sList: ArrayList<GetFriendRequestListModelData>,
    private var listenerClick: OnItemClick,
    ) : RecyclerView.Adapter<RVAdapterRequestList.ViewHolder>() {
    private lateinit var binding: CardGetRequestListBinding
    private var mContext: Context? = null
    var pic: String? = null


    fun addData(slist: ArrayList<GetFriendRequestListModelData>) {
        this.sList = slist
        notifyDataSetChanged()
    }

//    fun addDataPC(slist: ArrayList<GetCommentDataList>) {
//        for (item in slist.indices){
//
//            if(slist[item].parentId == 0L){
//                this.sList = slist
//            }
//            else{
//                this.childList = slist
//            }
//
//        }
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CardGetRequestListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        mContext = parent.context



//        this.mContext = context
        return ViewHolder(binding)
    }

//    override fun getItemViewType(position: Int): Int {
//        return position
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


                if (sList.isNullOrEmpty()) {
                    holder.bind.noData.visibility = View.VISIBLE
                    holder.bind.friendReqLayout.visibility = View.GONE
                } else {


                    val listPos = sList[position]
                    holder.bind.noData.visibility = View.GONE
                    holder.bind.friendReqLayout.visibility = View.VISIBLE



                    holder.bind.userNameReq.text = listPos.username ?: "Name"
                    holder.bind.userPicReq.load(listPos.userImage) {
                        placeholder(R.drawable.ic_baseline_person_24)
                        error(R.drawable.ic_baseline_person_24)
                    }



                    holder.onBind(listPos, listenerClick, position)

                }

    }


    override fun getItemCount(): Int =if(sList.size ==0){
        1
    }else  sList.size


    class ViewHolder(val bind: CardGetRequestListBinding) : RecyclerView.ViewHolder(bind.root) {
        fun onBind(model: GetFriendRequestListModelData, listener: OnItemClick, position: Int) {




            bind.acceptReq.setOnClickListener {
                listener.onAccept(model,position)

            }
            bind.cancelReq.setOnClickListener {
                listener.onReject(model,position)

            }
//            bind.liked.setOnClickListener {
//                listener.onLiked(model, position)
//
//                    bind.unlike.visibility = View.VISIBLE
//            }
//
//            bind.unlike.setOnClickListener {
//                listener.onUnLiked(model,position)
//                bind.unlike.visibility = View.GONE
//
//            }
//
//            bind.share.setOnClickListener {
//
//                listener.onShare(model, position)
//            }
//
//            bind.reply.setOnClickListener {
//                listener.onReply(model, position)
//            }
        }
    }


    interface OnItemClick {
        fun onAccept(click:GetFriendRequestListModelData,position: Int)
        fun onReject(click:GetFriendRequestListModelData,position: Int)
//        fun onLiked(comment: GetCommentDataList, position: Int)
//        fun onShare(comment: GetCommentDataList, position: Int)
//        fun onReply(comment: GetCommentDataList, position: Int)
//        fun onUnLiked(comment: GetCommentDataList,position: Int)

    }
}