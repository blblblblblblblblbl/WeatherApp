package com.blblblbl.myapplication.data

import android.util.Log
import com.example.example.Forecast

import com.example.example.ForecastResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ForecastRepository @Inject constructor(
    private val apiRepository: ApiRepository,
    private val databaseRepository: DatabaseRepository
) {

    suspend fun getForecast(city:String,days: Int):DBForecast? {
        try {
            val forecastResponse:ForecastResponse = apiRepository.getForecast(city,days)
            forecastResponse?.let {
                val dbForecast = DBForecast(forecastResponse.location!!.name!!,
                    forecastResponse.location!!.localtime!!,
                    forecastResponse.forecast!!
                )
                databaseRepository.db.forecastDao().insertNDelete(dbForecast)
                return dbForecast
            }
        }
        catch (e:Throwable){}
        val forecastFromDB = getForecastSaved(city)
        return forecastFromDB?:null
    }
    suspend fun getCurrent(loc:String):ForecastResponse{
        return apiRepository.getCurrent(loc)
    }
    fun getForecastsSavedList(): List<DBForecast> {

        return databaseRepository.db.forecastDao().getAll()
    }
    fun getForecastsCitiesSavedList(): List<String> {

        return databaseRepository.db.forecastDao().getAllCities()
    }

    suspend fun getForecastSaved(city: String): DBForecast {
        return databaseRepository.db.forecastDao().getForecast(city)
    }
}
