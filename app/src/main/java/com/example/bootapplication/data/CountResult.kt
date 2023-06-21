package com.example.bootapplication.data

sealed class CountResult {
    data class Content(val count: Long) : CountResult()
    data class Error(val throwable: Throwable) : CountResult()
}
