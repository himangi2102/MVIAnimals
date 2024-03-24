package com.example.mvianimals.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object AnimalService {
    const val BASE_URL="https://raw.githubusercontent.com/CatalinStefan/animalApi/master/"
    private fun getRetrofit()= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api:AnimalsApi= getRetrofit().create(AnimalsApi::class.java)
}