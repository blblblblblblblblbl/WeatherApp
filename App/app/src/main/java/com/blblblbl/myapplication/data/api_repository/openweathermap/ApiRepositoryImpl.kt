package com.blblblbl.myapplication.data.api_repository.openweathermap

import android.util.Log
import com.blblblbl.myapplication.data.api_repository.openweathermap.model.current.ForecastResponse
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepositoryImpl @Inject constructor(
    private val persistentStorageImpl: PersistentStorage
){
    object RetrofitServices{
        private const val BASE_URL= "https://api.openweathermap.org/"
        private val gson = GsonBuilder().setLenient().create()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val forecastApi: ForecastApi = retrofit.create(
            ForecastApi::class.java
        )
        interface ForecastApi{
            @GET("data/2.5/forecast")
            suspend fun getForecast(@Query("key") key:String, @Query("lat") lat:String, @Query("lon") lon:String, @Query("lang") lang:String=DEFAULT_LANGUAGE): ForecastResponse

            @GET("data/2.5/weather")
            suspend fun getCurrent(@Query("key") key:String, @Query("lat") lat:String, @Query("lon") lon:String, @Query("lang") lang:String=DEFAULT_LANGUAGE): ForecastResponse
        }
    }


     suspend fun getForecast(lat:String,lon: String): ForecastResponse {
        Log.d("MyLog","weather request")
         val lang = persistentStorageImpl.getProperty(PersistentStorageImpl.LANGUAGE_CODE)?:DEFAULT_LANGUAGE
        val response = RetrofitServices.forecastApi.getForecast(API_KEY,lat,lon,lang)
        Log.d("MyLog","weather response:$response")
        return response
    }
     suspend fun getCurrent(lat:String,lon: String): ForecastResponse {
        Log.d("MyLog","weather request")
        val lang = persistentStorageImpl.getProperty(PersistentStorageImpl.LANGUAGE_CODE)?:DEFAULT_LANGUAGE
        val response = RetrofitServices.forecastApi.getCurrent(API_KEY,lat,lon,lang)
        Log.d("MyLog","weather response:$response")
        return response
    }
    companion object{
        const val DEFAULT_LANGUAGE = "en"
        const val API_KEY = "2c4bb3733b875db220459fbd2432c7ce"
    }
}
