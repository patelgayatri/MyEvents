package com.devhome.myevents.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.devhome.myevents.R
import com.devhome.myevents.extensions.getNotificationID

class ReminderBroadcast : BroadcastReceiver() {
    private lateinit var builder: Notification.Builder
    lateinit var notificationManager: NotificationManager


    override fun onReceive(context: Context?, intent: Intent?) {

        notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationChannel: NotificationChannel
        val chanelId = context.packageName
        var description=intent?.extras?.getString("eventName")
        val notificationId = getNotificationID()

        println("====rec" + description)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                chanelId,
                description,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)


            builder = Notification.Builder(context, chanelId)
                .setContentTitle("My Event")
                .setContentText(description)
                .setSmallIcon(R.mipmap.ic_launcher)
        } else {
            builder = Notification.Builder(context)
                .setContentTitle("My Event")
                .setContentText(description)
                .setSmallIcon(R.mipmap.ic_launcher)
        }
        notificationManager.notify(notificationId, builder.build())


    }

}

