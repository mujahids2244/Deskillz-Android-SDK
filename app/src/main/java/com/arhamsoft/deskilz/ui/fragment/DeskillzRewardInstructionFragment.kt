package com.arhamsoft.deskilz.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.arhamsoft.deskilz.databinding.FragmentDeskillzRewardInstructionBinding

class DeskillzRewardInstructionFragment : Fragment() {

    private lateinit var binding: FragmentDeskillzRewardInstructionBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeskillzRewardInstructionBinding.inflate(layoutInflater)
        navController = findNavController()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
    }
}