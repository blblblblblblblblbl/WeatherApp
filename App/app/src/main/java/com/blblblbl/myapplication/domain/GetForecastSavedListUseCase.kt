package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.data.ForecastRepository
import com.blblblbl.myapplication.data.ForecastRepositoryImpl
import javax.inject.Inject

class GetForecastSavedListUseCase @Inject constructor(
    private val forecastRepositoryImpl: ForecastRepository
) {
    suspend fun execute(): List<DBForecast> {
        return forecastRepositoryImpl.getForecastsSavedList()
    }
    suspend fun executeCity(): List<String> {
        return forecastRepositoryImpl.getForecastsCitiesSavedList()
    }
}