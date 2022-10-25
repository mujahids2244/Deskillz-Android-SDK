package com.arhamsoft.deskilz.ui.fragment.navBarFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.FragmentCartScreenBinding

class CartScreenFragment : Fragment() {

    private lateinit var binding: FragmentCartScreenBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartScreenBinding.inflate(
            LayoutInflater.from(inflater.context),
            container, false
        )
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks() {
        binding.claimTokenLayout.setOnClickListener {
            navController.navigate(R.id.action_dashboardActivity_to_claimTokenFragment)
            //startActivity(Intent(requireContext(), ClaimTokenFragment::class.java))
        }

        binding.trophyLayout.setOnClickListener {
            navController.navigate(R.id.action_dashboardActivity_to_trophyScreenDetailFragment)
        }
    }


}