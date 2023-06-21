package com.example.bootapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Boot (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val timestamp: Long = System.currentTimeMillis()
)