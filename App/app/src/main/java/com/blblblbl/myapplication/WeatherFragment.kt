package com.blblblbl.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.viewmodels.WeatherViewModel
import com.example.example.Forecast
import com.example.example.Forecastday
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var dbForecast: DBForecast
    private lateinit var cityName:String
    private val viewModel:WeatherViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {cityName = it.getString(CITY_KEY).toString() }
        if (this::cityName.isInitialized){
            viewModel.getForecast(cityName)
        }
        lifecycleScope.launchWhenStarted { viewModel.forecast.collect{
            Log.d("MyLog", "collect from fragment" + it.toString())
        } }
        return ComposeView(requireContext()).apply {
            setContent {
                ForecastsList()
            }
        }
    }
    @Composable
    fun ForecastsList() {
        val forecast by viewModel._forecast.collectAsState(initial = null)
        LazyColumn{
            forecast?.let {
                itemsIndexed(forecast!!.forecast.forecastday) { index, item ->
                    if (item != null) {
                        item(item)
                    }
                }
            }

        }
    }
    @Composable
    fun item(forecast: Forecastday){
        Text(text = forecast?.date+": "+forecast.day?.mintempC+"..."+forecast.day?.maxtempC, modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
             )
    }
    companion object{
        const val CITY_KEY = "city_key"
    }
}