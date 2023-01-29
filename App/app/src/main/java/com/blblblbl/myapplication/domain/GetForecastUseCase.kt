package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.ForecastRepository
import com.blblblbl.myapplication.data.db.DBForecast
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val forecastRepositoryImpl: ForecastRepository
) {
    suspend fun execute(city:String,days:Int): DBForecast?{
       return forecastRepositoryImpl.getForecast(city,days)
    }

}