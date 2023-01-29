package com.blblblbl.myapplication.data

import com.example.example.ForecastResponse

interface ForecastRepository {
    suspend fun getForecast(city:String,days: Int):DBForecast?
    suspend fun getCurrent(loc:String): ForecastResponse
    suspend fun getCurrentSaved(): ForecastResponse?
    suspend fun saveCurrent(forecastResponse: ForecastResponse)
    fun getForecastsSavedList(): List<DBForecast>
    fun getForecastsCitiesSavedList(): List<String>
    suspend fun getForecastSaved(city: String): DBForecast
}