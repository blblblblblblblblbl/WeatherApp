package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.ForecastRepository
import com.example.example.ForecastResponse
import javax.inject.Inject

class SaveCurrentWeatherUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
) {
    suspend fun saveCurrentWeather(forecastResponse: ForecastResponse){
        forecastRepository.saveCurrent(forecastResponse)
    }
}