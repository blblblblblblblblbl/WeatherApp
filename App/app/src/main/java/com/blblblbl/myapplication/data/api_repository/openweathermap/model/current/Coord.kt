package com.blblblbl.myapplication.data.api_repository.openweathermap.model.current

import com.google.gson.annotations.SerializedName


data class Coord (

  @SerializedName("lon" ) var lon : Double? = null,
  @SerializedName("lat" ) var lat : Double? = null

)