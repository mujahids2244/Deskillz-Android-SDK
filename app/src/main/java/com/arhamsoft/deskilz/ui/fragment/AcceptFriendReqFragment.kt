package com.arhamsoft.deskilz.ui.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arhamsoft.deskilz.databinding.FragmentAcceptFriendReqBinding
import com.arhamsoft.deskilz.utils.LoadingDialog


class AcceptFriendReqFragment : Fragment() {

    lateinit var binding: FragmentAcceptFriendReqBinding
    lateinit var loading : LoadingDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAcceptFriendReqBinding.inflate(LayoutInflater.from(context))
        loading = LoadingDialog(requireContext() as Activity)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}