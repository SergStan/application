package com.example.bootapplication.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
    // TODO: implement writing into BD
    //  repository.insert(Boot())
    // inject by Koin or
    //  using maybe custom Static repository SERVICE
    //  or SQL lite without room here
    }


}