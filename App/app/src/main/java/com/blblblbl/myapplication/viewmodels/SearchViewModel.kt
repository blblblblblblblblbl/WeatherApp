package com.blblblbl.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.domain.GetForecastUseCase
import com.blblblbl.myapplication.domain.LastSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val lastSearchUseCase: LastSearchUseCase
):ViewModel() {
    suspend fun getForecast(city:String,days:Int):DBForecast?{
        return getForecastUseCase.execute(city.trim(),days)
    }
    fun getLastSearch(name:String):String?{
        return lastSearchUseCase.get(name)
    }
    fun setLastSearch(name:String,value:String){
        lastSearchUseCase.set(name,value)
    }
}