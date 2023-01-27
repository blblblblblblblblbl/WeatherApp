package com.blblblbl.myapplication.viewmodels

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.domain.*
import com.example.example.ForecastResponse
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val lastSearchUseCase: LastSearchUseCase,
    private val changeLocaleUseCase: ChangeLocaleUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val saveCurrentWeatherUseCase: SaveCurrentWeatherUseCase

):ViewModel() {
    val ph = PermissionHandler()
    private val _location = MutableStateFlow<Location?>(null)
    val location = _location.asStateFlow()
    private val _weather = MutableStateFlow<ForecastResponse?>(null)
    val weather = _weather.asStateFlow()
    suspend fun getForecast(city:String,days:Int):DBForecast?{
        return getForecastUseCase.execute(city.trim(),days)
    }
    fun getLastSearch(name:String):String?{
        return lastSearchUseCase.get(name)
    }
    fun setLastSearch(name:String,value:String){
        lastSearchUseCase.set(name,value)
    }
    fun setLocale(activity: FragmentActivity,locale: String){
        if (locale!=getLocale()){
            saveLocale(locale)
            activity.recreate()
        }
    }
    fun saveCurrentWeather(){
        viewModelScope.launch {
            _weather.value?.let { forecast->
                saveCurrentWeatherUseCase.saveCurrentWeather(forecast) }
        }
    }
    private fun saveLocale(locale:String){
        changeLocaleUseCase.saveLocale(locale)
    }
    private fun getLocale():String{
        return changeLocaleUseCase.getLocale()?:"en"
    }
    fun getLocation(context: Context){
        viewModelScope.launch {
            getLocationUseCase.getLocation(context,ph,_location)
        }
    }
    fun getCurrentSaved(){
        viewModelScope.launch{
            _weather.value = getCurrentWeatherUseCase.getSaved()
        }
    }
    fun getCurrentWeather(){
        viewModelScope.launch {
            location.value?.let { location ->
                _weather.value = getCurrentWeatherUseCase.getCurrentWeather("${location.latitude.toString()},${location.longitude.toString()}")
                Log.d("MyLog","current weather:${weather.value}")
                saveCurrentWeather()
            }

        }
    }

}