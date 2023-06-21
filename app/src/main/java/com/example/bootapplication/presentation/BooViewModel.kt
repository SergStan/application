package com.example.bootapplication.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bootapplication.data.AppRepository
import com.example.bootapplication.data.BootResult
import kotlinx.coroutines.launch


class BooViewModel(
    application: Application,
    private val repository: AppRepository
) : AndroidViewModel(application) {

    private val _result = MutableLiveData<MainState>()
    val result: LiveData<MainState> = _result

    fun loadHistory() {
        _result.value = MainState.Loading
        viewModelScope.launch {
            val result = repository.getAll()

            _result.value = when (result) {
                is BootResult.Content -> {
                    val list = result.boots
                    val stringBuilder = StringBuilder()
                    for (boot in list) {
                        stringBuilder.append("\u2022 ${boot.id} - ${boot.timestamp}\n")
                    }
                    MainState.Content(stringBuilder.toString())
                }

                is BootResult.Error -> {
                    MainState.Error(result.throwable)
                }
            }
        }
    }
}