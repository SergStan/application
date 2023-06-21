package com.example.bootapplication.data

import androidx.room.RoomDatabase
import androidx.room.Database

@Database(entities = [Boot::class], version = 1)
abstract class BootDatabase : RoomDatabase() {
    abstract fun bootDao(): BootDao
}