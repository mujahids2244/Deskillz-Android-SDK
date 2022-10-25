package com.arhamsoft.deskilz.ui.adapter

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.AdapterOpponentlistViewBinding
import com.arhamsoft.deskilz.databinding.AdapterTrophiesViewBinding
import com.arhamsoft.deskilz.databinding.RowHomeScreenBinding
import com.arhamsoft.deskilz.domain.models.WinLossModel
import com.arhamsoft.deskilz.networking.networkModels.GetMatchesRecord
import com.arhamsoft.deskilz.networking.networkModels.GetMatchesRecordData
import com.arhamsoft.deskilz.networking.networkModels.ListofOpponentModel

class AdapterTrophies(var listener: OnItemClickListenerHandler
): RecyclerView.Adapter<AdapterTrophies.Holder>() {
    private var sList: ArrayList<String> = ArrayList()



    fun setData(slist: ArrayList<String>) {
        this.sList = slist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTrophies.Holder {
        val binding = AdapterTrophiesViewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: AdapterTrophies.Holder, position: Int) {


        if (sList.isNullOrEmpty()) {
            holder.binding.noData.visibility = View.VISIBLE
            holder.binding.trophyLayout.visibility = View.GONE
        } else {

            val listPos = sList[position]

            holder.binding.noData.visibility = View.GONE
            holder.binding.trophyLayout.visibility = View.VISIBLE
//
//

                holder.binding.trophy.load(123){
                    placeholder(R.drawable.trophy_lar)
                    error(R.drawable.trophy_lar)
                }

//            holder.binding.oppoenetImg.load(listPos.opponentImage){
//                placeholder(R.drawable.ic_baseline_person_24)
//                error(R.drawable.ic_baseline_person_24)
//            }
//            holder.binding.oppoenetName.text = if (listPos.opponentName.isNullOrEmpty()){
//                "UnknownPlayer"
//            }
//            else{
//                listPos.opponentName
//            }
//            holder.binding.oppoenetFlag.load(listPos.)

            holder.onBind(sList[position], listener, position)

        }
    }





    override fun getItemCount(): Int = if(sList.size ==0){
        1
    }else  sList.size


    class Holder(val binding: AdapterTrophiesViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: String, listener:OnItemClickListenerHandler , position: Int) {

            binding.trophy.setOnClickListener {
                listener.onItemClicked(model,position)
            }
        }
    }

    interface OnItemClickListenerHandler {
        fun onItemClicked(click:String,position: Int)
    }

}