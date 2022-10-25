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
import com.arhamsoft.deskilz.databinding.*
import com.arhamsoft.deskilz.networking.networkModels.*

import java.util.Base64.getDecoder
import kotlin.collections.ArrayList


class RVAdapterRewardAllSubCategory(
    var context: Context,
    private var sList: ArrayList<ProductInfoModel>,
    private var listenerClick: OnItemClick,
    ) : RecyclerView.Adapter<RVAdapterRewardAllSubCategory.ViewHolder>() {
    private lateinit var binding: AdapterCartSkillRewardSubCatrgoryBinding
    private var mContext: Context? = null
    var pic: String? = null
//    private var sList: ArrayList<GetMarketLoadMoreModelData> = ArrayList()

    fun addData(slist: ArrayList<ProductInfoModel>) {
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
        binding = AdapterCartSkillRewardSubCatrgoryBinding.inflate(
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
        val productInfo = sList[position]
//        if (listPos.marketName.contains("All")){
           // for (i in 0 until listPos.productInfo.size) {
                holder.bind.nameTxt.text = productInfo.description
                holder.bind.imgSet.load(productInfo.imageProduct) {
                    placeholder(R.drawable.ic_baseline_person_24)
                }
                holder.bind.ticketPrice.text = productInfo.price
            //}
//        }
        holder.onBind(productInfo, listenerClick, position)
    }


    override fun getItemCount(): Int = if(sList.size ==0){
        1
    }else  sList.size


    class ViewHolder(val bind: AdapterCartSkillRewardSubCatrgoryBinding) : RecyclerView.ViewHolder(bind.root) {
        fun onBind(model: ProductInfoModel, listener: OnItemClick, position: Int) {



//            bind.seeMore.setOnClickListener{
//                listener.onClick(model,position)
//            }
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