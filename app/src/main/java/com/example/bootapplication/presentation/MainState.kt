package com.example.bootapplication.presentation

sealed class MainState {
    object Loading : MainState()
    data class Content(val boots: String): MainState()
    data class Error(val throwable: Throwable): MainState()
}
