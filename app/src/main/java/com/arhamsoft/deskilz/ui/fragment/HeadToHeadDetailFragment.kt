package com.arhamsoft.deskilz.ui.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.DialogHead2headBinding
import com.arhamsoft.deskilz.databinding.DialogOutOfFundsBinding
import com.arhamsoft.deskilz.databinding.FragmentHeadtoHeadDetailBinding

class HeadToHeadDetailFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentHeadtoHeadDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeadtoHeadDetailBinding.inflate(layoutInflater)
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }

        binding.playNow.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogHead2headBinding.inflate(layoutInflater)
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.okButton.setOnClickListener {
            showOutOfFundsDialog()
        }

        dialog.show()
    }

    private fun showOutOfFundsDialog() {
        val dialog = Dialog(requireContext())
        val bindingOutOfFunds = DialogOutOfFundsBinding.inflate(layoutInflater)
        dialog.setContentView(bindingOutOfFunds.root)
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        bindingOutOfFunds.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        bindingOutOfFunds.goToStore.setOnClickListener {

            val bundle = Bundle()
            bundle.putBoolean("FromOutOfFund", true)
            navController.navigate(R.id.action_headToHeadDetailFragment_to_dashboardActivity, bundle)

//            val intent = Intent(requireContext(), DashboardFragment::class.java)
//            intent.putExtra("FromOutOfFund", true)
//            startActivity(intent)
//            requireActivity().finishAffinity()
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    /*private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_head2head)

        val yesBtn = dialog.findViewById(R.id.ok_button) as RelativeLayout
        val noBtn = dialog.findViewById(R.id.cancel_button) as RelativeLayout

          yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            dialog.dismiss() }
        dialog.show()
    }*/
}