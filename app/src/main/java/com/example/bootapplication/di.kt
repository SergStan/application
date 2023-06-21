package com.example.bootapplication

import android.content.Context
import androidx.room.Room
import com.example.bootapplication.data.AppRepository
import com.example.bootapplication.data.BootDatabase
import com.example.bootapplication.data.BootRepository
import com.example.bootapplication.presentation.BooViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val DATA_BASE = "boot-database"
const val IO_DISPATCHER = "io"
const val MAIN_DISPATCHER = "main"

val bootModule = module {

    single<Context> { androidApplication() }
    single<CoroutineDispatcher>(named(IO_DISPATCHER)) { Dispatchers.IO }
    single<CoroutineDispatcher>(named(MAIN_DISPATCHER)) { Dispatchers.Main }
    single {
        Room.databaseBuilder(
            get(),
            BootDatabase::class.java,
            DATA_BASE
        ).build()
    }
    single {
        val database = get<BootDatabase>()
        database.bootDao()
    }
    single<AppRepository> { BootRepository(get(), get(named(IO_DISPATCHER))) }
    viewModel {
        BooViewModel(
            get(),
            get()
        )
    }
}
