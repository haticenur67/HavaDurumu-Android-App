package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class City(
    val id: Int,
    val name: String,
    val admin1: String? = null,
    val country: String? = null,
    val latitude: Double,
    val longitude: Double
)

data class CitySearchResponse(
    @SerializedName("results")
    val results: List<City>?
)