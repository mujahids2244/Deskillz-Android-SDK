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
import com.arhamsoft.deskilz.databinding.CardViewHeadtoheadBinding
import com.arhamsoft.deskilz.databinding.CardViewPracticeBinding
import com.arhamsoft.deskilz.networking.networkModels.GetChatsModel
import com.arhamsoft.deskilz.networking.networkModels.GetChatsModelData
import com.arhamsoft.deskilz.networking.networkModels.GetTournamentsListData

import java.util.Base64.getDecoder
import kotlin.collections.ArrayList


class RVAdapterHeadToHead(
    var context: Context,
    private var sList: ArrayList<GetTournamentsListData>,
    private var listenerClick: OnItemClick,
    ) : RecyclerView.Adapter<RVAdapterHeadToHead.ViewHolder>() {
    private lateinit var binding: CardViewHeadtoheadBinding
    private var mContext: Context? = null
    var pic: String? = null


    fun addData(slist: ArrayList<GetTournamentsListData>) {
        this.sList = slist
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CardViewHeadtoheadBinding.inflate(
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


        val listPos = sList[position]


//        holder.bind.pracImg.load(listPos.tournamentImage){
//            placeholder(R.drawable.coin_medium)
//        }
        holder.bind.img.load(listPos.tournamentImage){
            placeholder(R.drawable.onedollarlarge)
            error(R.drawable.onedollarlarge)
        }
        holder.bind.tournamentName.text = listPos.tournamentName
        holder.bind.entryFee.text = " Entry $${listPos.entryFee} "
        holder.bind.player.text = "${listPos.playerCount} players"
        holder.bind.prize.text = "Prize: ${listPos.winningPrize}"



        holder.onBind(listPos, listenerClick, position)

    }


    override fun getItemCount(): Int = if(sList.size ==0){
        1
    }else  sList.size


    class ViewHolder(val bind: CardViewHeadtoheadBinding) : RecyclerView.ViewHolder(bind.root) {
        fun onBind(model: GetTournamentsListData, listener: OnItemClick, position: Int) {


            bind.play.setOnClickListener{
                listener.onClick(model,position)
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
        fun onClick(click:GetTournamentsListData,position: Int)
//        fun onLiked(comment: GetCommentDataList, position: Int)
//        fun onShare(comment: GetCommentDataList, position: Int)
//        fun onReply(comment: GetCommentDataList, position: Int)
//        fun onUnLiked(comment: GetCommentDataList,position: Int)

    }
}