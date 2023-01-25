package com.blblblbl.myapplication.domain

import android.util.Log
import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.data.ForecastRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
) {
    suspend fun execute(city:String,days:Int):DBForecast?{
       return forecastRepository.getForecast(city,days)
    }
}