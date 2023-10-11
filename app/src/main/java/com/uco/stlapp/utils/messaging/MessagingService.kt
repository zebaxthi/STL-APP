package com.uco.stlapp.utils.messaging

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.messaging.RemoteMessage
import com.uco.stlapp.R
import com.uco.stlapp.views.activities.MainActivity

const val channelId = "notification_chaneel"
const val channelName = "com.uco.stlapp.utils.messaging"
@Suppress("UNUSED_EXPRESSION")
class MessagingService : FirebaseMessagingService() {

    @SuppressLint("RemoteLayout")
    fun getRemoteView(tittle: String, message: String): RemoteViews{

        val remoteView = RemoteViews("com.uco.stlapp.utils.messaging",R.layout.notification)

        remoteView.setTextViewText(R.id.title,tittle)
        remoteView.setTextViewText(R.id.message,message)
        remoteView.setImageViewResource(R.id.app_logo,R.drawable.loan_logo)

        return remoteView
    }
    fun generateNotification(title: String, message: String){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity( this,  0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        // channel id, channel name
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.loan_logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel= (channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0,builder.build())
    }

}
