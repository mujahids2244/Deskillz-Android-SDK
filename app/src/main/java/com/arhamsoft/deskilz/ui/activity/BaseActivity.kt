package com.arhamsoft.deskilz.ui.activity

import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.arhamsoft.deskilz.AppController
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.databinding.ActivityBaseBinding
import com.arhamsoft.deskilz.databinding.DialogDepositBinding
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.utils.LogoutHandler
import com.arhamsoft.deskilz.utils.LogoutInterface
import com.arhamsoft.deskilz.utils.StaticFields

open class BaseActivity : AppCompatActivity(), LogoutInterface {

    private lateinit var binding: ActivityBaseBinding
    private lateinit var navController: NavController
    lateinit var sharedPreference: CustomSharedPreference

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).apply {
            StaticFields.key = metaData.getString("GameId").toString()
            Log.e("gameKeyStringOnCreate1", "onCreate:${StaticFields.key}..String " )
            if (StaticFields.key.isEmpty() || StaticFields.key == "null") {
                StaticFields.key = metaData.getInt("GameId").toString()
                Log.e("gameKeyIntOnCreate1", "onCreate:${StaticFields.key}..Int " )

            }
            URLConstant.gameActivity = metaData.getString("gameActivity").toString()
        }
        LogoutHandler.setListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityBaseBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).apply {
            StaticFields.key = metaData.getString("GameId").toString()
            Log.e("gameKeyStringOnCreate2", "onCreate:${StaticFields.key}..String " )

            if (StaticFields.key.isEmpty() || StaticFields.key == "null") {
                StaticFields.key = metaData.getInt("GameId").toString()
                Log.e("gameKeyIntOnCreate2", "onCreate:${StaticFields.key}..Int " )

            }
            URLConstant.gameActivity = metaData.getString("gameActivity").toString()
        }
//        val fragmentHost = supportFragmentManager.findFragmentById(binding.navGraph.id) as NavHostFragment
//        navController = fragmentHost.navController

        LogoutHandler.setListener(this)

//        if (intent.extras != null) {
//            val bundle2 = bundleOf()
//
//            val map = HashMap<String, String>()
//
//            for (key in intent.extras!!.keySet()) {
//                intent.extras!!.getString(key)?.let { map.put(key, it) }
//            }
//
//            if (map["notificationType"]!!.toInt() == 4){
////                navController.navigate(R.id.startSDKFragment)
//
//            }
//            else if (map["notificationType"]!!.toInt() == 3){
//                sharedPreference.saveLogin("LOGIN", false)
////                navController.navigate(R.id.startSDKFragment)
//            }
//            else if (map["notificationType"]!!.toInt() == 0){
//                bundle2.putInt("GLOBAL_CHAT", 1)
//
////                navController.navigate(R.id.chatFragment)
//
//            }
//            else if (map["notificationType"]!!.toInt() == 1){
//                bundle2.putInt("GLOBAL_CHAT", 2)
//                bundle2.putSerializable("FRIEND_ID", map["fromId"])
////                navController.navigate(R.id.chatFragment)
//
//            }
//        }
    }

    override fun logoutListener() {

        runOnUiThread {
            showDialog()
        }
    }


    private fun showDialog() {
        val dialog = Dialog(this)
        val dialogBinding = DialogDepositBinding.inflate(layoutInflater)
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.para.visibility = View.GONE
        dialogBinding.h1.text ="You have logged-In in another device"
        dialogBinding.price.visibility = View.GONE
        dialogBinding.cancelButton.visibility = View.GONE
        dialogBinding.okButton.text = "OK"
        dialogBinding.okButton.setOnClickListener {
            dialog.dismiss()
//            navController.clearBackStack(R.id.switchAccountFragment)
//            navController.popBackStack(R.id.switchAccountFragment,true)
           val size= navController.backQueue.size
            Log.e("stackSize", "showDialog:$size " )
            for (item in 0 until navController.backQueue.size) {
                navController.popBackStack()
            }
            navController.navigate(R.id.signInFragment)
//            startActivity(Intent(this@BaseActivity,BaseActivity::class.java))
//            this.finishAffinity()

        }

        dialog.show()
    }

}