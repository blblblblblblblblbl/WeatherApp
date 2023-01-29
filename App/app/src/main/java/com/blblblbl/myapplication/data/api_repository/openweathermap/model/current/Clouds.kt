package com.blblblbl.myapplication.data.api_repository.openweathermap.model.current

import com.google.gson.annotations.SerializedName


data class Clouds (

  @SerializedName("all" ) var all : Int? = null

)