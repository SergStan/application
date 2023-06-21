package com.example.bootapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BootDao {

    @Query("SELECT * FROM boot")
    fun getAll(): List<Boot>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Boot: Boot)

    @Query("SELECT COUNT(id) FROM boot")
    fun getCount(): Long
}