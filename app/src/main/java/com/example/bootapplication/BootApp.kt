package com.example.bootapplication

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.example.bootapplication.domain.NotificationJobService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BootApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        doJobNotification()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            koin.loadModules(
                listOf(
                    bootModule
                )
            )
        }
    }

    private fun doJobNotification() {
        val mScheduler: JobScheduler =
            (getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler)
        val builder = JobInfo.Builder(
            JOB_ID,
            ComponentName(applicationContext, NotificationJobService::class.java)
        )
        val jobInfo = builder
            .setMinimumLatency(PERIODIC)
            .setPersisted(true)
            .build()
        mScheduler.schedule(jobInfo)
    }

    companion object {
        private const val JOB_ID = 1001
        private const val PERIODIC: Long = 15 * 60 * 1000
    }
}