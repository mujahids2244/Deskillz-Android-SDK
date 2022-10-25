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
import android.widget.Toast
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.AdapterCartSkillRewardAllcategorysBinding
import com.arhamsoft.deskilz.databinding.AdapterCartSkillRewardsBinding
import com.arhamsoft.deskilz.databinding.CardGetAllUserListsBinding
import com.arhamsoft.deskilz.databinding.CardViewChatBinding
import com.arhamsoft.deskilz.networking.networkModels.*
import com.arhamsoft.deskilz.utils.StaticFields

import java.util.Base64.getDecoder
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class RVAdapterRewardAllCategory(
    var context: Context,
    private var listenerClick: OnItemClick,
    ) : RecyclerView.Adapter<RVAdapterRewardAllCategory.ViewHolder>() {
    private lateinit var binding: AdapterCartSkillRewardAllcategorysBinding
    private var mContext: Context? = null
    var pic: String? = null
    private var sList: ArrayList<GetMarketLoadMoreModelData> = ArrayList()


    fun addData(slist: ArrayList<GetMarketLoadMoreModelData>) {
        this.sList = slist
        notifyDataSetChanged()
    }

    fun loadMoreDataInSub(slist: GetMarketLoadMoreModelData, position: Int) {
        this.sList[position].productInfo.addAll(slist.productInfo)
        notifyItemChanged(position)
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
        binding = AdapterCartSkillRewardAllcategorysBinding.inflate(
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (sList.isNullOrEmpty()){
//                Toast.makeText(holder.itemView.context,"not available",Toast.LENGTH_SHORT).show()

        }
        else{


        binding.allCategoryRecycler.adapter  =
            RVAdapterRewardAllSubCategory(binding.allCategoryRecycler.context,
                sList[position].productInfo,
                object: RVAdapterRewardAllSubCategory.OnItemClick {
                override fun onClick(click: GetMarketLoadMoreModelData, position: Int) {
                    //listenerClick.onClick(click,position)
                }
            })
//        (holder.bind.childComment.adapter as RVAdapterCommentChild).notifyItemChanged(position)
//        (holder.bind.childComment.adapter as RVAdapterCommentChild).notifyItemInserted(position)
        //(holder.bind.allCategoryRecycler.adapter as RVAdapterRewardAllSubCategory).notifyDataSetChanged()
        binding.marketName.text = sList[position].marketName

//        val listPos = sList[position]
////        if (listPos.marketName.contains("All")){
//            for (i in 0 until listPos.productInfo.size) {
//                holder.bind.nameTxt.text = listPos.productInfo[i].description
//                holder.bind.imgSet.load(listPos.productInfo[i].imageProduct) {
//                    placeholder(R.drawable.ic_baseline_person_24)
//                }
//                holder.bind.ticketPrice.text = listPos.productInfo[i].price
//            }
////        }
        holder.onBind(sList[position], listenerClick, position)
    }
    }


    override fun getItemCount(): Int = if(sList.size ==0){
        1
    }else  sList.size


    class ViewHolder(val bind: AdapterCartSkillRewardAllcategorysBinding) : RecyclerView.ViewHolder(bind.root) {
        fun onBind(model: GetMarketLoadMoreModelData, listener: OnItemClick, position: Int) {



            bind.seeMore.setOnClickListener{
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
//
        }
    }


    interface OnItemClick {
        fun onClick(click:GetMarketLoadMoreModelData,position: Int)
//        fun onLiked(comment: GetCommentDataList, position: Int)
//        fun onShare(comment: GetCommentDataList, position: Int)
//        fun onReply(comment: GetCommentDataList, position: Int)
//        fun onUnLiked(comment: GetCommentDataList,position: Int)

    }
}