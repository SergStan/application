package com.example.bootapplication.domain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.bootapplication.R

class NotificationJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {

        // TODO: implement getting from BD

        //        repository.getAll()
        // inject by Koin or
        //  using maybe custom Static repository SERVICE


        // TODO: prepare logic according task with getting empty or first or last with average in case last

        //        var id: String = ""
        //        var timestamp: String = ""


        var id: String = ""
        var timestamp: String = ""


        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                ID,
                NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).let {
                notificationManager.createNotificationChannel(it)
            }
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(),
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(this, ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(id)
            .setContentText(timestamp)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0, builder.build())
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    companion object{
        private const val ID = "id"
        private const val NAME = "name"
    }
}