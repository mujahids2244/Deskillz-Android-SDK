package com.arhamsoft.deskilz.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.AdapterOpponentlistViewBinding

import com.arhamsoft.deskilz.networking.networkModels.ListofOpponentModel

class AdapterOpponents(var listener: OnItemClickListenerHandler
): RecyclerView.Adapter<AdapterOpponents.Holder>() {
    private var sList: ArrayList<ListofOpponentModel> = ArrayList()



    fun setData(slist: ArrayList<ListofOpponentModel>) {
        this.sList = slist
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
//        if (sList.isNullOrEmpty()) {
//            holder.binding.noData.visibility = View.VISIBLE
//            holder.binding.competitivePlayer.visibility = View.GONE
//        }else{

            val listPos = sList[position]

            holder.binding.noData.visibility = View.GONE
            holder.binding.competitivePlayer.visibility = View.VISIBLE


            holder.binding.oppoenetImg.load(listPos.opponentImage) {
                placeholder(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
            }
            holder.binding.oppoenetName.text = if (listPos.opponentName.isNullOrEmpty()) {
                "UnknownPlayer"
            } else {
                listPos.opponentName
            }
//            holder.binding.oppoenetFlag.load(listPos.)

            holder.onBind(sList[position], listener, position)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterOpponents.Holder {
        val binding = AdapterOpponentlistViewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return Holder(binding)
    }

//    override fun onBindViewHolder(holder: AdapterOpponents.Holder, position: Int) {
//
//
////        if (sList.isNullOrEmpty()){
////            holder.binding.noData.visibility = View.VISIBLE
////            holder.binding.competitivePlayer.visibility = View.GONE
////        }else{
//
//            val listPos = sList[position]
//
//            holder.binding.noData.visibility = View.GONE
//            holder.binding.competitivePlayer.visibility = View.VISIBLE
//
//
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
////            holder.binding.oppoenetFlag.load(listPos.)
//
//            holder.onBind(sList[position], listener,position)
//
//        }



//    }

    override fun getItemCount(): Int =  sList.size


    class Holder(val binding: AdapterOpponentlistViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: ListofOpponentModel, listener:OnItemClickListenerHandler , position: Int) {

            binding.oppoenetName.setOnClickListener {
                listener.onItemClicked(model,position)
            }
        }
    }

    interface OnItemClickListenerHandler {
        fun onItemClicked(click:ListofOpponentModel,position: Int)
    }

}