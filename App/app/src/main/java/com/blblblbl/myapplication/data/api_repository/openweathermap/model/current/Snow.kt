package com.blblblbl.myapplication.data.api_repository.openweathermap.model.current

import com.google.gson.annotations.SerializedName


data class Snow (

  @SerializedName("1h" ) var oneHour : Double? = null

)