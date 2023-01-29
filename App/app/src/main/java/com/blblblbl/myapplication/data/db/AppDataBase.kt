package com.blblblbl.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [DBForecast::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}