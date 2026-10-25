package com.example.myapplication.data

import com.example.myapplication.model.ForecastResponse

class WeatherRepository {
    private val api = ApiClient.forecastApi

    suspend fun getForecast(lat: Double, lon: Double): ForecastResponse? {
        return try {
            api.getForecast(lat, lon)
        } catch (e: Exception) {
            null
        }
    }
}