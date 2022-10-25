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
import com.arhamsoft.deskilz.databinding.CardViewChatBinding
import com.arhamsoft.deskilz.networking.networkModels.GetChatsModel
import com.arhamsoft.deskilz.networking.networkModels.GetChatsModelData
import com.arhamsoft.deskilz.networking.networkModels.ReceivedDataFromSocket
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import java.text.SimpleDateFormat
import java.util.*

import java.util.Base64.getDecoder
import kotlin.collections.ArrayList


class RVAdapterComment(
    var context: Context,
    private var sList: ArrayList<ReceivedDataFromSocket>,
    private var date:String,
    private var listenerClick: OnItemClick,
    ) : RecyclerView.Adapter<RVAdapterComment.ViewHolder>() {
    private lateinit var binding: CardViewChatBinding
    private var mContext: Context? = null
    var pic: String? = null


    fun addData(slist: ArrayList<ReceivedDataFromSocket>) {
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
        binding = CardViewChatBinding.inflate(
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
//        val updatedChildList: ArrayList<GetCommentDataList> = ArrayList()

//        updatedChildList.clear()

        //child comments acc to their parent
//        if (childList != null && childList.isNotEmpty()) {
//            for (item in childList) {
//                if (item.parentId == sList[position].commentId) {
//                    updatedChildList.add(item)
//
//                }
//            }
//        }
//        rvAdapterChildComment2.addData(childList)

        val listPos = sList[position]


        if (listPos.userId==URLConstant.u_id){
            //my msg
            holder.bind.userMsgLayout.visibility = View.VISIBLE
            holder.bind.otherMsgLayout.visibility = View.GONE

            holder.bind.myMessage.text = listPos.message
            holder.bind.myMessageTime.text = if (listPos.createdAtDate.startsWith(date)){
                 listPos.createdAtTime

            }
            else{
//                 SimpleDateFormat("yyyy-MM-dd hh:mm a")
                 "${listPos.createdAtDate}, ${listPos.createdAtTime}"

            }
//           val dt = SimpleDateFormat("yyyy-MM-ddThh:mm:sssZ").parse(listPos.createdAt)?.time




        }
        else{
            //other msgs
            holder.bind.userMsgLayout.visibility = View.GONE
            holder.bind.otherMsgLayout.visibility = View.VISIBLE

//            if (listPos.userCountryImage?.contains("data:image/jpeg;base64,") == true) {
//
//                pic = listPos.userCountryImage
//            }
//            val imageBytes: ByteArray = Base64.decode(pic, Base64.DEFAULT)
//            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//            holder.bind.countryImg.setImageBitmap(decodedImage)

            binding.userPic.load(listPos.userImage){
                placeholder(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
            }
            holder.bind.otherMsgs.text = listPos.message
            holder.bind.UserName.text = listPos.senderName
//            2022-09-19T08:00:02.878Z
            holder.bind.myMessageTime.text = if (listPos.createdAtDate.startsWith(date)){
                 listPos.createdAtTime
            }
            else{
//                SimpleDateFormat("yyyy-MM-dd hh:mm a")
                "${listPos.createdAtDate}, ${listPos.createdAtTime}"
            }
//            holder.bind.time.text = SimpleDateFormat("yyyy-MM-ddThh:mm:ss").parse(listPos.createdAt)?.time.toString()


        }


        holder.onBind(listPos, listenerClick, position)

    }


    override fun getItemCount(): Int =  sList.size


    class ViewHolder(val bind: CardViewChatBinding) : RecyclerView.ViewHolder(bind.root) {
        fun onBind(model: ReceivedDataFromSocket, listener: OnItemClick, position: Int) {


            bind.userPic.setOnClickListener{
                listener.onOthersClick(model,position)
            }

            bind.userMsgLayout.setOnClickListener{
                listener.onUserClick(model,position)
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
        fun onOthersClick(click:ReceivedDataFromSocket,position: Int)
        fun onUserClick(click:ReceivedDataFromSocket,position: Int)
//        fun onLiked(comment: GetCommentDataList, position: Int)
//        fun onShare(comment: GetCommentDataList, position: Int)
//        fun onReply(comment: GetCommentDataList, position: Int)
//        fun onUnLiked(comment: GetCommentDataList,position: Int)

    }
}