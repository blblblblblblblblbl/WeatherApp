package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.ForecastRepository
import com.example.example.ForecastResponse
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: ForecastRepository
) {
    suspend fun getCurrentWeather(loc:String):ForecastResponse{
        return repository.getCurrent(loc)
    }
    suspend fun getSaved():ForecastResponse?{
        return repository.getCurrentSaved()
    }
}