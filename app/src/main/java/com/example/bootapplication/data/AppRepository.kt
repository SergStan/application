package com.example.bootapplication.data


interface AppRepository {
   suspend fun getAll(): BootResult
   suspend fun insertBoot(boot: Boot): Boolean
}