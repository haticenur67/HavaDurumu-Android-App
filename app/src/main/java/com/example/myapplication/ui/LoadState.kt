package com.example.myapplication.ui

sealed class LoadState<out T> {
    object Loading : LoadState<Nothing>()
    data class Success<T>(val data: T) : LoadState<T>()
    object Empty : LoadState<Nothing>()
    data class Error(val message: String) : LoadState<Nothing>()
}