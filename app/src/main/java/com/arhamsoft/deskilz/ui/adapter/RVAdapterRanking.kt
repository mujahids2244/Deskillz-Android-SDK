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

class RVAdapterRanking(var listener: OnItemClickListenerHandler
): RecyclerView.Adapter<RVAdapterRanking.Holder>() {
    private var sList: ArrayList<PlayerRankingModelData> = ArrayList()
    var adpterCheck:Int = 0



    fun setData2(slist: ArrayList<PlayerRankingModelData>, check:Int) {
        this.sList= slist
        this.adpterCheck = check
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapterRanking.Holder {
        val binding = RowNotificationsBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RVAdapterRanking.Holder, position: Int) {



            if (sList.isNullOrEmpty()) {
                holder.binding.noData.setTextColor(R.color.black)
                holder.binding.noData.visibility = View.VISIBLE
                holder.binding.rankingLayout.visibility = View.GONE
                holder.binding.layout.visibility = View.GONE
            } else {


                val listPos = sList[position]

                holder.binding.noData.visibility = View.GONE
                holder.binding.rankingLayout.visibility = View.VISIBLE
                holder.binding.layout.visibility = View.GONE

                holder.binding.rank.text = "Rank: ${listPos.SDK_Rank.toString()}"
                holder.binding.rowNo.text = (position + 1).toString()
                holder.binding.rankemail.text = listPos.userEmail
                holder.binding.rankuserName.text = if(listPos.userName.isNullOrEmpty()){
                    "Unknown Player"
                }
                else{
                    listPos.userName
                }
                holder.binding.rankFlag.load(listPos.countryFlag){
                    placeholder(R.drawable.country_icon)
                    error(R.drawable.country_icon)
                }

                holder.binding.rankuserImg.load(listPos.profileImage){
                    placeholder(R.drawable.ic_baseline_person_24)
                    error(R.drawable.ic_baseline_person_24)
                }




//            holder.binding.oppoenetName.text = listPos.usernameOtheruser
//            holder.binding.entryFee.text = listPos.entryFee

                holder.onBind(sList[position], listener, position)

            }





    }

    override fun getItemCount(): Int = if(sList.size ==0){
        1
    }else  sList.size


    class Holder(val binding: RowNotificationsBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: PlayerRankingModelData, listener:OnItemClickListenerHandler , position: Int) {

            binding.layout.setOnClickListener {
                listener.onItemClicked(model,position)
            }
        }
    }

    interface OnItemClickListenerHandler {
        fun onItemClicked(click:PlayerRankingModelData,position: Int)
    }

}