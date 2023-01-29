package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.data.ForecastRepositoryImpl
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val forecastRepositoryImpl: ForecastRepositoryImpl
) {
    suspend fun execute(city:String,days:Int):DBForecast?{
       return forecastRepositoryImpl.getForecast(city,days)
    }

}