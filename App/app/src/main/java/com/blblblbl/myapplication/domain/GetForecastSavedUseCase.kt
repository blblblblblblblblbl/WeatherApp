package com.blblblbl.myapplication.domain

import android.util.Log
import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.data.ForecastRepository
import javax.inject.Inject

class GetForecastSavedUseCase@Inject constructor(
    private val forecastRepository: ForecastRepository
) {
    suspend fun execute(city:String): DBForecast {
        return forecastRepository.getForecastSaved(city)
    }
}