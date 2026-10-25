package com.example.myapplication.model

object WeatherCondition {
    fun get(code: Int): Pair<String, String> {
        return when (code) {
            0 -> "☀️" to "Açık"
            1, 2, 3 -> "⛅" to "Parçalı Bulutlu"
            45, 48 -> "🌫️" to "Sisli"
            51, 53, 55, 56, 57 -> "🌧️" to "Çiseleyen Yağmur"
            61, 63, 65, 66, 67 -> "🌧️" to "Yağmurlu"
            71, 73, 75, 77 -> "❄️" to "Kar Yağışlı"
            80, 81, 82 -> "🌦️" to "Sağanak Yağış"
            95, 96, 99 -> "⛈️" to "Fırtınalı"
            else -> "☁️" to "Bilinmiyor"
        }
    }
}