package com.arhamsoft.deskilz.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arhamsoft.deskilz.databinding.FragmentTestSkillsScreenBinding

class TestSkillsFragment: Fragment() {

    private lateinit var binding: FragmentTestSkillsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestSkillsScreenBinding.inflate(layoutInflater)
        return binding.root
    }

}