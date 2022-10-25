package com.arhamsoft.deskilz.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arhamsoft.deskilz.databinding.FragmentTrophyScreenDetailsBinding

class TrophyScreenDetailFragment : Fragment() {

    private lateinit var binding: FragmentTrophyScreenDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrophyScreenDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

}