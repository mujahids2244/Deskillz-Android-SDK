package com.arhamsoft.deskilz.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentResultScreenBinding
import com.arhamsoft.deskilz.networking.networkModels.GetMatchesRecordData
import com.arhamsoft.deskilz.networking.networkModels.ListofOpponentsMatchesRecord
import com.arhamsoft.deskilz.ui.adapter.AdapterResultScreen
import com.arhamsoft.deskilz.ui.fragment.navBarFragments.HomeScreenFragment
import com.arhamsoft.deskilz.utils.StaticFields

class GameResultFragment: Fragment() {

    private lateinit var binding: FragmentResultScreenBinding
    var click :GetMatchesRecordData? = null

    lateinit var rvAdapter: AdapterResultScreen

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultScreenBinding.inflate(layoutInflater)


        binding.recycleListresult.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        rvAdapter = AdapterResultScreen(object : AdapterResultScreen.OnItemClickListenerHandler {
            override fun onItemClicked(click: ListofOpponentsMatchesRecord, position: Int) {

            }
        })


        binding.recycleListresult.adapter = rvAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val fragment = HomeScreenFragment()
        val bundle = arguments
        if (bundle != null) {

            click = bundle.getSerializable("MATCHRECORDOBJECT") as GetMatchesRecordData

//            val matchId = bundle.getString("MATCHID")
//            val tickets = bundle.getLong("TICKETS")
//            val xpOpponent = bundle.getString("XPOPPONENT")
//            val xpUser= bundle.getString("XPUSER")

            binding.position.text = click!!.placePosition
            binding.xpvalueUser.text = click!!.xpValueUser.toString()
            binding.tournName.text = click!!.matchTitle
            binding.date.text = click!!.time
            binding.matchId.text = click!!.matchId
            binding.fee.text = "Entry Fee: ${click!!.entryFee}"

            rvAdapter.setData(click!!.listofOpponent as ArrayList<ListofOpponentsMatchesRecord>)

            Log.e("matchid", "${click!!.matchId} ")
        }else{
            StaticFields.toastClass("Error while retrieving data.")
        }

        binding.backtoDashboard.setOnClickListener {
            findNavController().popBackStack()
        }

                binding.gotoReportScreen.setOnClickListener {
                    val bundle2 = bundleOf()
                    bundle2.putSerializable("MATCH_ID",click?.matchId)
            findNavController().navigate(R.id.action_gameResultFragment_to_reportPlayerFragment,bundle2)
        }

    }





}