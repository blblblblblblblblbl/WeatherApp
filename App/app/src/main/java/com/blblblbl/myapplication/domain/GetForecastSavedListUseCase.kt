package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.data.ForecastRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForecastSavedListUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
) {
    suspend fun execute(): List<DBForecast> {
        return forecastRepository.getForecastsSavedList()
    }
    suspend fun executeCity(): List<String> {
        return forecastRepository.getForecastsCitiesSavedList()
    }
}