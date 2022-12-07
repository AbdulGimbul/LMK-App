package com.abdl.mylmk_app

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val title = "Yuk cek jadwal belajar!"
        val notifId = 100

        showToast(context, type, message)

        if (message != null) {
            showAlarmNotification(context, title, message, notifId)
        }
    }

    private fun showToast(context: Context, title: String?, message: String?) {
        Toast.makeText(context, "$title : $message", Toast.LENGTH_SHORT).show()
    }

    fun setNotifAlarm(context: Context, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).also {
            it.putExtra(EXTRA_MESSAGE, message)
        }

        val calendar = Calendar.getInstance()

        val time = calendar.timeInMillis + (1000 * 60 * 60 * 24)

        val pendingIntent =
            PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }

    private fun showAlarmNotification(
        context: Context,
        title: String,
        message: String,
        notifId: Int
    ) {

        val channelId = "channel_1"
        val channelName = "AlarmManager channel"

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 10000, 10000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)
    }

    companion object {
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TYPE = "extra_type"
        const val ID = 100
    }
}