package com.blblblbl.myapplication.data.persistent_storage.utils

import com.blblblbl.myapplication.data.util.GsonParser
import com.blblblbl.myapplication.data.util.JsonParser
import com.example.example.ForecastResponse
import com.google.gson.GsonBuilder

object StorageConverter {
    private val gson = GsonBuilder().setLenient().create()
    private val jsonParser: JsonParser = GsonParser(gson)
    fun forecastInfoToJson(forecastResponse: ForecastResponse) : String? {
        return jsonParser.toJson(
            forecastResponse,
            ForecastResponse::class.java
        )
    }
    fun forecastInfoFromJson(json: String): ForecastResponse? {
        return jsonParser.fromJson<ForecastResponse>(
            json,
            ForecastResponse::class.java
        )
    }
}