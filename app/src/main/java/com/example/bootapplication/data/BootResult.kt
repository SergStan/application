package com.example.bootapplication.data

sealed class BootResult {
    data class Content(val boots: List<Boot>) : BootResult()
    data class Error(val throwable: Throwable) : BootResult()
}
