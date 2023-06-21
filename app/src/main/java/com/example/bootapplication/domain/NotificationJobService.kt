package com.example.bootapplication.domain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.bootapplication.IO_DISPATCHER
import com.example.bootapplication.R
import com.example.bootapplication.data.AppRepository
import com.example.bootapplication.data.BootResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent

class NotificationJobService : JobService() {

    private val repository: AppRepository = KoinJavaComponent.getKoin().get()
    private val io: CoroutineDispatcher = KoinJavaComponent.getKoin().get(named(IO_DISPATCHER))

    override fun onStartJob(params: JobParameters?): Boolean {

        var title: String = applicationContext.resources.getString(R.string.init_title)
        var message: String = applicationContext.resources.getString(R.string.init_message)

        runBlocking {
            launch(io) {
                when (val result = repository.getAll()) {
                    is BootResult.Content -> {
                        val list = result.boots
                        message = when (list.size) {
                            ZERO_SIZE -> {
                                title = applicationContext.resources.getString(R.string.empty)
                                applicationContext.resources.getString(R.string.no_boots_detected)
                            }

                            SINGLE -> {
                                title = applicationContext.resources.getString(R.string.first)
                                applicationContext.resources.getString(R.string.one_boots_detected) + list[FIRST_ELEMENT_INDEX].timestamp
                            }

                            else -> {
                                title = applicationContext.resources.getString(R.string.average)
                                val average =
                                    (list[FIRST_ELEMENT_INDEX].timestamp + list[SECOND_ELEMENT_INDEX].timestamp) / AVERAGE_DIVIDER
                                applicationContext.resources.getString(R.string.one_boots_detected) + average
                            }
                        }
                    }

                    is BootResult.Error -> {
                        title = applicationContext.resources.getString(R.string.error_notification)
                        message = applicationContext.resources.getString(R.string.error)
                    }
                }
            }
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
        notificationManager.notify(2, builder.build())
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    companion object {
        private const val ID = "id"
        private const val NAME = "name"
        private const val ZERO_SIZE = 0
        private const val SINGLE = 1
        private const val AVERAGE_DIVIDER = 2
        private const val FIRST_ELEMENT_INDEX = 0
        private const val SECOND_ELEMENT_INDEX = 1
    }
}