package com.arhamsoft.deskilz.ui.adapter

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.RowHomeScreenBinding
import com.arhamsoft.deskilz.databinding.RowResultUserProfileBinding
import com.arhamsoft.deskilz.domain.models.WinLossModel
import com.arhamsoft.deskilz.networking.networkModels.GetMatchesRecord
import com.arhamsoft.deskilz.networking.networkModels.GetMatchesRecordData
import com.arhamsoft.deskilz.networking.networkModels.ListofOpponentsMatchesRecord

class AdapterResultScreen(var listener: OnItemClickListenerHandler
): RecyclerView.Adapter<AdapterResultScreen.Holder>() {
    private var sList: ArrayList<ListofOpponentsMatchesRecord> = ArrayList()



    fun setData(slist: ArrayList<ListofOpponentsMatchesRecord>) {
        this.sList = slist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterResultScreen.Holder {
        val binding = RowResultUserProfileBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: AdapterResultScreen.Holder, position: Int) {


        if (sList.isNullOrEmpty()){
            holder.binding.noData.visibility = View.VISIBLE
            holder.binding.opponentLayout.visibility = View.GONE
        }else{

            val listPos = sList[position]

            holder.binding.noData.visibility = View.GONE
            holder.binding.opponentLayout.visibility = View.VISIBLE


         holder.binding.oppoenetName.text = listPos.usernameOtheruser.ifEmpty{
             "Unknown Player"
         }

            holder.binding.opponentImg.load(listPos.userProfileImageOtherUser){
                placeholder(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
            }
            holder.binding.opponentXp.text = listPos.xpValueOther.toString().ifEmpty {
                "N/A"
            }
            holder.binding.oppoenetFlag.load(123){
                placeholder(R.drawable.country_icon)
                error(R.drawable.country_icon)
            }

            holder.onBind(sList[position], listener,position)

        }



    }

    override fun getItemCount(): Int = if(sList.size ==0){
        1
    }else  sList.size


    class Holder(val binding: RowResultUserProfileBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: ListofOpponentsMatchesRecord, listener:OnItemClickListenerHandler , position: Int) {

//            binding.home.setOnClickListener {
//                listener.onItemClicked(model,position)
//            }
        }
    }

    interface OnItemClickListenerHandler {
        fun onItemClicked(click:ListofOpponentsMatchesRecord,position: Int)
    }

}