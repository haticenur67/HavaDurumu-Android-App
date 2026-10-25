package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val current: CurrentWeather,
    val hourly: HourlyForecast,
    val daily: DailyForecast
)

data class CurrentWeather(
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("weather_code") val weatherCode: Int
)

data class DailyForecast(
    val time: List<String>,
    @SerializedName("weather_code") val weatherCode: List<Int>,
    @SerializedName("temperature_2m_max") val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min") val temperatureMin: List<Double>
)

data class HourlyForecast(
    val time: List<String>,
    @SerializedName("temperature_2m") val temperature: List<Double>
)