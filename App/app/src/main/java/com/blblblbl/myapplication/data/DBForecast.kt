package com.blblblbl.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.example.Forecast
import com.google.gson.annotations.SerializedName

@Entity(tableName = "forecasts", primaryKeys = ["city","upload_date"])

data class DBForecast(
    @ColumnInfo(name = "city")
    @SerializedName("city" )
    val city:String,

    @ColumnInfo(name = "upload_date")
    @SerializedName("upload_date" )
    val upload_date:String,

    @ColumnInfo(name = "forecast")
    @SerializedName("forecast" )
    val forecast: Forecast
    )

