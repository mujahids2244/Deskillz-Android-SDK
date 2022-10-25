package com.arhamsoft.deskilz.services

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder

import com.arhamsoft.deskilz.AppController
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import androidx.navigation.fragment.findNavController
import com.arhamsoft.deskilz.ui.fragment.DashboardFragment
import com.arhamsoft.deskilz.ui.fragment.SignInFragment
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.utils.LogoutHandler

class MyFirebaseMessagingService : FirebaseMessagingService() {


    var sharedPreference: CustomSharedPreference =
        CustomSharedPreference(AppController.getContext())

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    private fun isOnForeGround(): Boolean {
        val check = ActivityManager.RunningAppProcessInfo()
        return check.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND ||
                check.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.notification?.title
        val descriptionText = message.notification?.body

        val notification = NotificationBuilder()
        notification.createNotificationChannel(AppController.getContext(), message.senderId!!)

        val params: Map<*, *> = message.data
        val json = JSONObject(params)

        Log.e("notficationData= ", "onMessageReceived:$json " )

        val n_type = if (json.has("notificationType"))
            json["notificationType"] as String
        else "-1"

        val fromId = if (json.has("fromId"))
            json["fromId"] as String
        else ""


        if(n_type.toInt() == 5){

            val pendingIntent = NavDeepLinkBuilder(this)
                .setGraph(R.navigation.app_navigation)
                .setDestination(R.id.chatHeadsFragment)
                .createPendingIntent()

            if (message.notification != null) {
                notification.onReceive(this, pendingIntent, title!!, descriptionText!!)
            }
            return
        }

        if(n_type.toInt() == 6){
            if (message.notification != null) {
                notification.onReceive(this, null, title!!, descriptionText!!)
            }
            return
        }


        if(n_type.toInt() == 4){

            val pendingIntent = NavDeepLinkBuilder(this)
                .setGraph(R.navigation.app_navigation)
                .setDestination(R.id.dashboardActivity)
                .createPendingIntent()

            if (message.notification != null) {
                notification.onReceive(this, pendingIntent, title!!, descriptionText!!)
            }
            return
        }

        if (n_type.toInt() == 3){
            sharedPreference.saveLogin("LOGIN", false)
            if (isOnForeGround()) {
                URLConstant.u_id = "0"
                LogoutHandler.triggerInterface()

                if (message.notification != null) {
                    notification.onReceive(this, null, title!!, descriptionText!!)
                }
            }
            return
        }


        if (URLConstant.joinType != n_type.toInt()) {

            val requestID = System.currentTimeMillis()
            var intent1 = Intent()
            val bundle2 = bundleOf()
            if (n_type.toInt() == 0) {

                bundle2.putInt("GLOBAL_CHAT", 1)
//                intent1 = Intent(this, ChatFragment::class.java).apply {
//                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                }
            } else if (n_type.toInt() == 1) {

                bundle2.putInt("GLOBAL_CHAT", 2)
                bundle2.putSerializable("FRIEND_ID", fromId)
//                intent1 = Intent(this, ChatFragment::class.java).apply {
//                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                }
            }

//
//             intent = Intent(this, StartSDKFragment::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            }

            val pendingIntent = NavDeepLinkBuilder(this)
                .setGraph(R.navigation.app_navigation)
                .setDestination(R.id.chatFragment)
                .setArguments(bundle2)
                .createPendingIntent()

//            val pendingIntent: PendingIntent = PendingIntent.getActivity(this,
//                requestID.toInt(),intent1,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//            val title = message.notification?.title
//            val descriptionText = message.notification?.body
//
//            val notification = NotificationBuilder()
//            notification.createNotificationChannel(AppController.getContext(), message.senderId!!)

            if (message.notification != null) {
                notification.onReceive(this, pendingIntent, title!!, descriptionText!!)
            }

        }

        if (URLConstant.joinType == 1 && n_type.toInt() == 1) {
            if (URLConstant.chatHeadId != fromId) {

                var intent2 = Intent()
                val bundle2 = bundleOf()
                if (n_type.toInt() == 0) {

                    bundle2.putInt("GLOBAL_CHAT", 1)
//                    intent2 = Intent(this, ChatFragment::class.java).apply {
//                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    }
                } else if (n_type.toInt() == 1) {

                    bundle2.putInt("GLOBAL_CHAT", 2)
                    bundle2.putSerializable("FRIEND_ID", fromId)
//                    intent2 = Intent(this, ChatFragment::class.java).apply {
//                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    }
                }
                val pendingIntent = NavDeepLinkBuilder(this)
                    .setGraph(R.navigation.app_navigation)
                    .setDestination(R.id.chatFragment)
                    .setArguments(bundle2)
                    .createPendingIntent()

//                val pendingIntent: PendingIntent = PendingIntent.getActivity(
//                    this,
//                    0,
//                    intent2,
//                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
//                    bundle2
//                )
//                val title = message.notification?.title
//                val descriptionText = message.notification?.body
//
//                val notification = NotificationBuilder()
//                notification.createNotificationChannel(
//                    AppController.getContext(),
//                    message.senderId!!
//                )

                if (message.notification != null) {
                    notification.onReceive(this, pendingIntent, title!!, descriptionText!!)
                }

            }
        }


    }

}