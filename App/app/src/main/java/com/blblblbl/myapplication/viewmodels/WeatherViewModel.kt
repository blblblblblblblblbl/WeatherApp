package com.blblblbl.myapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.domain.GetForecastSavedUseCase
import com.example.example.Forecastday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getForecastSavedUseCase: GetForecastSavedUseCase
):ViewModel() {
    val _forecast = MutableStateFlow<DBForecast?>(null)
    val forecast = _forecast.asStateFlow()

    fun getForecast(city:String){
        Log.d("MyLog","getForecastSavedUseCase"+getForecastSavedUseCase.toString())
        viewModelScope.launch {
            _forecast.value = getForecastSavedUseCase.execute(city.replace(" ",""))
            Log.d("MyLog","_forecast.value"+getForecastSavedUseCase.execute(city).toString())
                 }
    }
}