package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.data.ForecastRepository
import com.blblblbl.myapplication.data.ForecastRepositoryImpl
import javax.inject.Inject

class GetForecastSavedUseCase@Inject constructor(
    private val forecastRepositoryImpl: ForecastRepository
) {
    suspend fun execute(city:String): DBForecast {
        return forecastRepositoryImpl.getForecastSaved(city)
    }
}