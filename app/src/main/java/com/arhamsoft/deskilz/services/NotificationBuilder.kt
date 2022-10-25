package com.arhamsoft.deskilz.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.arhamsoft.deskilz.R


const val channelID = "channel1"

class NotificationBuilder {
    var notificationId = -1
    var message:String? =""


    fun createNotificationChannel(context: Context, id:String) {
        notificationId = id.toLong().toInt()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           val title = "Title"
            val desc = "Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, title, importance).apply {
                description = desc
            }
            // Register the channel with the system
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun onReceive(context: Context, pendingIntent: PendingIntent?,title:String , desc:String) {

//        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.alarm)
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_app_icon_deskillz)
            .setContentTitle(title)
            .setContentText(desc)
            .setSound(alarmSound)
//            .setLargeIcon(bitmap)
//            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
//            notificationId++
        }
    }
}