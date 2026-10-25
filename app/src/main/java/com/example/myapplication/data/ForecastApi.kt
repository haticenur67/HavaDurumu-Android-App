package com.example.myapplication.data

import com.example.myapplication.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "temperature_2m,weather_code",
        @Query("hourly") hourly: String = "temperature_2m",
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min",
        @Query("timezone") timezone: String = "auto"
    ): ForecastResponse
}