package com.example.myapplication.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.WeatherRepository
import com.example.myapplication.model.ForecastResponse
import com.example.myapplication.ui.LoadState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForecastViewModel : ViewModel() {
    private val repository = WeatherRepository()
    private val _uiState = MutableStateFlow<LoadState<ForecastResponse>>(LoadState.Loading)
    val uiState: StateFlow<LoadState<ForecastResponse>> = _uiState

    fun fetchForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.value = LoadState.Loading
            val result = repository.getForecast(lat, lon)
            if (result != null) {
                _uiState.value = LoadState.Success(result)
            } else {
                _uiState.value = LoadState.Error("Hava durumu alınamadı.")
            }
        }
    }
}