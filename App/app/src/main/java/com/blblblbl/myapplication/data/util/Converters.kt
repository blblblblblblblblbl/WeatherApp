package com.blblblbl.myapplication.data.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.example.Forecast

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun toMeaningJson(forecast: Forecast) : String {
        return jsonParser.toJson(
            forecast,
            Forecast::class.java
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): Forecast{
        return jsonParser.fromJson<Forecast>(
            json,
            Forecast::class.java
        ) ?: Forecast()
    }
}