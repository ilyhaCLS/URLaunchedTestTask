package com.youarelaunched.challenge.ui.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.youarelaunched.challenge.network.api.NetworkDataProvider
import com.youarelaunched.challenge.network.models.NetworkVendor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "MainScreenViewModel"
private const val VENDORS_ERROR_MSG = "Error getting data"

sealed interface MainScreenEvent {
    data object RetryButtonPressed: MainScreenEvent
}

sealed interface MainScreenState {
    data object Loading: MainScreenState
    data class Ready(val vendors: List<NetworkVendor>): MainScreenState
    data class Error(val message: String): MainScreenState
}

class MainScreenViewModel(context: Context) : ViewModel() {
    private val dataProvider: NetworkDataProvider = NetworkDataProvider(Dispatchers.IO, context)

    private val _uiState: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState.Loading)
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: MainScreenEvent) {
        when(event) {
            is MainScreenEvent.RetryButtonPressed -> {
                loadData()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            dataProvider.getVendors()
                .onSuccess { result ->
                    Log.d(TAG, "$result")
                    _uiState.update { MainScreenState.Ready(result) }
                }
                .onFailure { exception ->
                    Log.e(TAG, "Exception: $exception")
                    _uiState.update { MainScreenState.Error(VENDORS_ERROR_MSG) }
                }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application: Application? = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                if(application == null) {
                    throw RuntimeException("MainScreenViewModel: Factory did not get Application instance")
                } else {
                    MainScreenViewModel(application.applicationContext)
                }
            }
        }
    }
}