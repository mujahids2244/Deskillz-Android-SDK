package com.arhamsoft.deskilz.ui.adapter

import android.text.SpannableString
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.RowHomeScreenBinding
import com.arhamsoft.deskilz.databinding.RowNotificationsBinding
import com.arhamsoft.deskilz.domain.models.WinLossModel
import com.arhamsoft.deskilz.networking.networkModels.*

class RVAdapterNotification(var listener: OnItemClickListenerHandler
): RecyclerView.Adapter<RVAdapterNotification.Holder>() {
    private var sList: ArrayList<NotificationModelData> = ArrayList()


    fun setData(slist: ArrayList<NotificationModelData>) {
        this.sList = slist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapterNotification.Holder {
        val binding = RowNotificationsBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RVAdapterNotification.Holder, position: Int) {


            if (sList.isNullOrEmpty()) {
                holder.binding.noData.visibility = View.VISIBLE
                holder.binding.rankingLayout.visibility = View.GONE
                holder.binding.layout.visibility = View.GONE
            } else {


                val listPos = sList[position]

                holder.binding.noData.visibility = View.GONE
                holder.binding.rankingLayout.visibility = View.GONE
                holder.binding.layout.visibility = View.VISIBLE




            holder.binding.notifUserImg.load(listPos.notificationimage){
                placeholder(R.drawable.ic_baseline_person_24)
            }
            holder.binding.notifTitle.text = listPos.notificationTitle
                holder.binding.notifDescription.text = listPos.notificationText
                holder.binding.notifTime.visibility = View.GONE

                holder.onBind(sList[position], listener, position)

            }






    }

    override fun getItemCount(): Int = if(sList.size ==0){
        1
    }else  sList.size


    class Holder(val binding: RowNotificationsBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: NotificationModelData, listener:OnItemClickListenerHandler , position: Int) {

            binding.layout.setOnClickListener {
                listener.onItemClicked(model,position)
            }
        }
    }

    interface OnItemClickListenerHandler {
        fun onItemClicked(click:NotificationModelData,position: Int)
    }

}