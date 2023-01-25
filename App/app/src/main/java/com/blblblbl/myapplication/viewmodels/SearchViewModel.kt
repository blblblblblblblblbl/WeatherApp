package com.blblblbl.myapplication.viewmodels

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.domain.ChangeLocaleUseCase
import com.blblblbl.myapplication.domain.GetForecastUseCase
import com.blblblbl.myapplication.domain.LastSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val lastSearchUseCase: LastSearchUseCase,
    private val changeLocaleUseCase: ChangeLocaleUseCase
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
    fun setLocale(activity: FragmentActivity,locale: String){
        if (locale!=getLocale()){
            saveLocale(locale)
            activity.recreate()
        }
    }
    fun saveLocale(locale:String){
        changeLocaleUseCase.saveLocale(locale)
    }
    fun getLocale():String{
        return changeLocaleUseCase.getLocale()?:"en"
    }
}