package com.blblblbl.myapplication.data.api_repository.openweathermap.model.forecast

import com.google.gson.annotations.SerializedName


data class Snow (

  @SerializedName("3h" ) var threeHours : Double? = null

)