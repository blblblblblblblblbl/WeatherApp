package com.blblblbl.myapplication.data

import android.util.Log
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import com.example.example.ForecastResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val persistentStorageImpl: PersistentStorageImpl
) {
    object RetrofitServices{
        private const val BASE_URL= "https://api.weatherapi.com/v1/"
        private val gson = GsonBuilder().setLenient().create()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val forecastApi:ForecastApi = retrofit.create(
            ForecastApi::class.java
        )

        interface ForecastApi{
            @GET("forecast.json")
            suspend fun getForecast(@Query("key") key:String, @Query("q") q:String, @Query("days") days:Int, @Query("aqi") aqi:String, @Query("alerts") alerts:String): ForecastResponse

            @GET("current.json")
            suspend fun getCurrent(@Query("key") key:String, @Query("q") q:String, @Query("aqi") aqi:String,@Query("lang") lang:String="DEFAULT_LANGUAGE"): ForecastResponse

        }

    }


    suspend fun getForecast(city:String,days: Int):ForecastResponse {
        Log.d("MyLog","weather request")
        val response = RetrofitServices.forecastApi.getForecast(API_KEY,city,days,"no","no")
        Log.d("MyLog","weather response:$response")
        return response
    }
    suspend fun getCurrent(loc:String):ForecastResponse{
        Log.d("MyLog","weather request")
        val lang = persistentStorageImpl.getProperty(PersistentStorageImpl.LANGUAGE_CODE)?:"DEFAULT_LANGUAGE"
        val response = RetrofitServices.forecastApi.getCurrent(API_KEY,loc,"no",lang)
        Log.d("MyLog","weather response:$response")
        return response
    }
    companion object{
        const val DEFAULT_LANGUAGE = "en"
        const val API_KEY = "5f6574d1b7f94bd99d042815232501"
    }
}