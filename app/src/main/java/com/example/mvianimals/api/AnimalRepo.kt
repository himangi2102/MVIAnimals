package com.example.mvianimals.api

class AnimalRepo(private val api: AnimalsApi) {
    suspend fun getAnimals()=api.getAnimals()
}