package com.blblblbl.myapplication.data.api_repository

import android.util.Log
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import com.example.example.ForecastResponse

interface ApiRepository {
    suspend fun getForecast(city:String,days: Int): ForecastResponse
    suspend fun getCurrent(loc:String): ForecastResponse
}