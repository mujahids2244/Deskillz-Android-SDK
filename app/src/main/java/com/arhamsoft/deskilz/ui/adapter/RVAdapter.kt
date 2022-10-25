/*
package com.arhamsoft.deskilz.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.deskilz.databinding.CardViewChatBinding

import java.util.*


class RVAdapter(var context: Context,private var listenerClick: OnItemClick) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {


    private lateinit var binding: CardViewChatBinding
    private var mContext: Context? = null


//    fun addData(slist: ArrayList<>) {
////        this.sList = slist
//
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

//        holder.onBind(listPos, listenerClick, position)

    }


    override fun getItemCount(): Int = sList.size

    class ViewHolder(val bind: CardViewChatBinding) : RecyclerView.ViewHolder(bind.root) {
        fun onBind( listener: OnItemClick, position: Int) {


            bind.myMessage.setOnClickListener {
                listener.onClick( position)
            }
        }
    }


    interface OnItemClick {

        fun onClick(position: Int)

    }
}*/
