package com.example.mvianimals.view

sealed class MainState {
    object Idle:MainState()
    object Loading:MainState()
    data class Animals(val animals: List<com.example.mvianimals.model.Animal>):MainState()
    data class Error(val error:String?):MainState()
}