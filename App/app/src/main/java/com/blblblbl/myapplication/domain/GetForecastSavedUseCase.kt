package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.db.DBForecast
import com.blblblbl.myapplication.data.ForecastRepository
import javax.inject.Inject

class GetForecastSavedUseCase@Inject constructor(
    private val forecastRepositoryImpl: ForecastRepository
) {
    suspend fun execute(city:String): DBForecast {
        return forecastRepositoryImpl.getForecastSaved(city)
    }
}