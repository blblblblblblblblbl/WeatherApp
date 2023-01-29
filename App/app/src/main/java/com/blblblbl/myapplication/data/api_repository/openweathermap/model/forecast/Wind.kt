package com.blblblbl.myapplication.data.api_repository.openweathermap.model.forecast

import com.google.gson.annotations.SerializedName


data class Wind (

  @SerializedName("speed" ) var speed : Double? = null,
  @SerializedName("deg"   ) var deg   : Int?    = null,
  @SerializedName("gust"  ) var gust  : Double? = null

)