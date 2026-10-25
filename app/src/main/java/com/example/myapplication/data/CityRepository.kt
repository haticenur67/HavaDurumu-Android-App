package com.example.myapplication.data

import com.example.myapplication.model.City

// Repository Deseni: Veri kaynakları (API) ile iş mantığı (ViewModel)
// arasındaki bağı koparan, veri yönetimini tek merkezde toplayan katman
class CityRepository {
    private val api = ApiClient.cityApi

    suspend fun search(query: String): List<City> {
        return try {
            val response = api.searchCity(name = query)
            response.results ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}