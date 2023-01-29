package com.blblblbl.myapplication.data.api_repository.weatherapi

import android.util.Log
import com.blblblbl.myapplication.data.api_repository.ApiRepository
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import com.example.example.ForecastResponse
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepositoryImpl @Inject constructor(
    private val persistentStorageImpl: PersistentStorage
): ApiRepository {
    object RetrofitServices{
        private const val BASE_URL= "https://api.weatherapi.com/v1/"
        private val gson = GsonBuilder().setLenient().create()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val forecastApi: ForecastApi = retrofit.create(
            ForecastApi::class.java
        )

        interface ForecastApi{
            @GET("forecast.json")
            suspend fun getForecast(@Query("key") key:String, @Query("q") q:String, @Query("days") days:Int, @Query("aqi") aqi:String, @Query("alerts") alerts:String): ForecastResponse

            @GET("current.json")
            suspend fun getCurrent(@Query("key") key:String, @Query("q") q:String, @Query("aqi") aqi:String,@Query("lang") lang:String="DEFAULT_LANGUAGE"): ForecastResponse

        }

    }


    override suspend fun getForecast(city:String, days: Int):ForecastResponse {
        Log.d("MyLog","weather request")
        val response = RetrofitServices.forecastApi.getForecast(API_KEY,city,days,"no","no")
        Log.d("MyLog","weather response:$response")
        return response
    }
    override suspend fun getCurrent(loc:String):ForecastResponse{
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
@Module
@InstallIn(SingletonComponent::class)
abstract class ApiRepositoryModule{
    @Binds
    abstract fun bindApiRepository(apiRepositoryImpl: ApiRepositoryImpl): ApiRepository
}