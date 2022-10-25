package com.arhamsoft.deskilz.ui.fragment

import android.R
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alphaCareInc.app.room.UserDatabase
import com.arhamsoft.deskilz.databinding.DialogDepositBinding
import com.arhamsoft.deskilz.databinding.FragmentReportPlayerBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.ForgotModel
import com.arhamsoft.deskilz.networking.networkModels.GetMatchesRecordData
import com.arhamsoft.deskilz.networking.networkModels.NotificationModel
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ReportPlayerFragment : Fragment() {

    lateinit var binding: FragmentReportPlayerBinding
    var arr = arrayListOf<String>()
    var category: String? = null
    var u_id:String? = null
    lateinit var loading: LoadingDialog
    var matchID:String? =""




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportPlayerBinding.inflate(LayoutInflater.from(context))
    loading= LoadingDialog(requireContext() as Activity)
        choiceSpinner()
        val bundle = arguments
        if (bundle != null) {

           matchID = bundle.getSerializable("MATCH_ID") as String?
            binding.matchid.text = matchID

//            Log.e("matchid", "${click!!.matchId} ")
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            if (user != null) {
//                u_id = user.userId
//            }
//        }

        binding.btnSubmit.setOnClickListener {

            if (binding.text.text.isEmpty()){
                binding.text.error="please fill the text"
            }
            else {
            loading.startLoading()
            reportPlayer()
            }
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }



        return binding.root




    }


    private fun showDialog(t:String) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogDepositBinding.inflate(layoutInflater)
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.para.visibility =View.GONE
        dialogBinding.h1.text ="Report Status"
        dialogBinding.price.text = "${t} "
        dialogBinding.cancelButton.visibility = View.GONE
        dialogBinding.okButton.text = "OK"
        dialogBinding.okButton.setOnClickListener {
dialog.dismiss()
        }

        dialog.show()
    }


    private fun choiceSpinner() {

        arr.add("Account Inquiry")
        arr.add("Billing Inquiry")
        arr.add("Contact Us")
        arr.add("Report a Crash")
        arr.add("Report Abuse")
        arr.add("Report Bug")
        arr.add("Deskillz Account Deletion")


        val countrySpinner = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            arr
        )

        countrySpinner.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.categorySpinner.adapter = countrySpinner

        binding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    positiont: Int,
                    id: Long
                ) {
                    category = parent!!.getItemAtPosition(positiont).toString()

                    (parent.getChildAt(0) as TextView).setTextColor(Color.BLACK)

//                    binding.categorySpinner.setSelection(positiont)

                    Log.e("category", "onItemSelected: $category " )

//                    countryID = positiont.toLong() + 1
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("nothing", "nothing Selected")
                }
            }
    }

    private fun reportPlayer(){

        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.reportPlayer(
                URLConstant.u_id!!,
                matchID!!,
                category!!,
                binding.text.text.toString(),
                object : NetworkListener<ForgotModel> {
                    override fun successFul(t: ForgotModel) {
                        loading.isDismiss()
                        activity?.runOnUiThread {

                        if (t.status == 1) {
                                showDialog("Your report has been submitted")

                            }
                            else{
                            StaticFields.toastClass(t.message)
                        }
                        }
                    }
                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Api syncing fail reportPlayer")
                        }
                    }
                }
            )
        }


    }



}