package com.devhome.myevents.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.fragment.app.FragmentManager
import com.devhome.myevents.R
import com.devhome.myevents.extensions.getNotificationID
import com.devhome.myevents.ui.MainActivity

class ReminderBroadcast : BroadcastReceiver() {
    private lateinit var builder: Notification.Builder
    lateinit var notificationManager: NotificationManager


    override fun onReceive(context: Context?, intent: Intent?) {

        notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationChannel: NotificationChannel
        var description = intent?.extras?.getString("eventName")
        val chanelId = context.packageName + description

        val notificationId = getNotificationID()

        println("====rec" + description)
        val icon = BitmapFactory.decodeResource(context.resources,R.mipmap.ic_launcher)

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
                .setContentTitle("You have one event today")
                .setContentText(description)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, MainActivity::class.java),
                        0
                    )
                )
        } else {
            builder = Notification.Builder(context)
                .setContentTitle("You have one event today")
                .setContentText(description)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, MainActivity::class.java),
                        0
                    )
                )

        }
        notificationManager.notify(notificationId, builder.build())


    }

}

