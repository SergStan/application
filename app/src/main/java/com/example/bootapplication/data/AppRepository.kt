package com.example.bootapplication.data


interface AppRepository {
   suspend fun getAll(): BootResult
   suspend fun insertBoot(): Boolean
   suspend fun getCount(): CountResult
}