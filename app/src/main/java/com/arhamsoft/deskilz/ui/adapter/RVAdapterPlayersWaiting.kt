package com.arhamsoft.deskilz.ui.adapter

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.RowHomeScreenBinding
import com.arhamsoft.deskilz.databinding.RowNotificationsBinding
import com.arhamsoft.deskilz.databinding.RowPendingRequestBinding
import com.arhamsoft.deskilz.domain.models.WinLossModel
import com.arhamsoft.deskilz.networking.networkModels.GetMatchesRecord
import com.arhamsoft.deskilz.networking.networkModels.GetMatchesRecordData
import com.arhamsoft.deskilz.networking.networkModels.PlayerWaitingModel
import com.arhamsoft.deskilz.networking.networkModels.PlayerWaitingModelData

class RVAdapterPlayersWaiting(var listener: OnItemClickListenerHandler
): RecyclerView.Adapter<RVAdapterPlayersWaiting.Holder>() {
    private var sList: ArrayList<PlayerWaitingModelData> = ArrayList()



    fun setData(slist: ArrayList<PlayerWaitingModelData>) {
        this.sList = slist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapterPlayersWaiting.Holder {
        val binding = RowPendingRequestBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RVAdapterPlayersWaiting.Holder, position: Int) {


        if (sList.isNullOrEmpty()){
            holder.binding.noData.visibility = View.VISIBLE
            holder.binding.layout.visibility = View.GONE
        }else{

            val listPos = sList[position]

            holder.binding.noData.visibility = View.GONE
            holder.binding.layout.visibility = View.VISIBLE
            holder.binding.tournName.text = listPos.tournamentName
            holder.binding.playerFound.text = listPos.waitingFor

            holder.binding.img.load(listPos.tournamentImage){
                placeholder(R.drawable.onedollarlarge)
                error(R.drawable.onedollarlarge)
            }
//            if (listPos.playerCount >=4){
//                holder.binding.play.visibility = View.VISIBLE
//                holder.binding.playerFound.text = "Player Found - Tap to Play"
//            }
//            else{
//                holder.binding.play.visibility = View.GONE
//                holder.binding.playerFound.text = listPos.waitingFor
//
//            }
//
//            if (listPos.isPlayed){
//                holder.binding.score.visibility = View.VISIBLE
//                holder.binding.score.text = "Previous Score: ${listPos.previousScore}"
////                holder.binding.prize.visibility =View.GONE
//                holder.binding.entryFee.visibility = View.GONE
//                holder.binding.player.visibility = View.GONE
//            }
//            else{
                holder.binding.score.visibility = View.VISIBLE
//                holder.binding.prize.visibility =View.VISIBLE
                holder.binding.entryFee.visibility = View.VISIBLE
                holder.binding.player.visibility = View.VISIBLE
                holder.binding.score.text = "Your Score: ${listPos.previousScore}"

//                holder.binding.prize.text = "Prize: ${listPos.winningPrize}"
                holder.binding.entryFee.text = "Fee: ${listPos.entryFee}"
                holder.binding.player.text = "Player: ${listPos.playerCount}"
//            }



//            holder.binding.oppoenetName.text = listPos.usernameOtheruser
//            holder.binding.entryFee.text = listPos.entryFee

            holder.onBind(sList[position], listener,position)

        }



    }

    override fun getItemCount(): Int = if(sList.size ==0){
        1
    }else  sList.size


    class Holder(val binding: RowPendingRequestBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: Any, listener:OnItemClickListenerHandler , position: Int) {

//            binding.play.setOnClickListener {
//                listener.onItemClicked(model,position)
//            }
        }
    }

    interface OnItemClickListenerHandler {
        fun onItemClicked(click:Any,position: Int)
    }

}