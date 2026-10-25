package com.example.myapplication.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CityRepository
import com.example.myapplication.model.City
import com.example.myapplication.ui.LoadState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// MVVM Mimarisi: Kullanıcı arama yaptığında API ile iletişim kuran ve
// UI durumlarını (Loading, Success, Error) güncelleyen ViewModel sınıfı.
class CityViewModel : ViewModel() {
    private val repository = CityRepository()
    private var searchJob: Job? = null

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _uiState = MutableStateFlow<LoadState<List<City>>>(LoadState.Empty)
    val uiState: StateFlow<LoadState<List<City>>> = _uiState

    fun onQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()

        if (query.trim().length < 3) {
            _uiState.value = LoadState.Empty
            return
        }

        searchJob = viewModelScope.launch {
            _uiState.value = LoadState.Loading
            delay(500)

            try {
                val results = repository.search(query)
                if (results.isEmpty()) {
                    _uiState.value = LoadState.Empty
                } else {
                    _uiState.value = LoadState.Success(results)
                }
            } catch (e: Exception) {
                _uiState.value = LoadState.Error("Bağlantı hatası oluştu.")
            }
        }
    }
}