package com.example.bootapplication.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.bootapplication.IO_DISPATCHER
import com.example.bootapplication.data.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin


class BootCompletedReceiver : BroadcastReceiver() {

    private val repository: AppRepository = getKoin().get()
    private val io: CoroutineDispatcher = getKoin().get(named(IO_DISPATCHER))

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("android.intent.action.BOOT_COMPLETED"))
            runBlocking {
                launch(io) {
                    repository.insertBoot()
                }
            }
    }
}