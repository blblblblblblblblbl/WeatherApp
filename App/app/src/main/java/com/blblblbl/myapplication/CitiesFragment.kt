package com.blblblbl.myapplication

import android.os.Bundle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blblblbl.myapplication.viewmodels.CitiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesFragment : Fragment() {
    private val viewModel:CitiesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getForecasts()
        return ComposeView(requireContext()).apply {
            setContent {
                ForecastsList()
            }
        }
    }
    @Composable
    fun ForecastsList() {
        val list = viewModel.cityNames.collectAsState(initial = emptyList())
        LazyColumn{
            itemsIndexed(list.value){index,item->
                if (item != null) {
                    item(item)
                }
            }
        }
    }
    @Composable
    fun item(city: String){
        Text(text = city, modifier = Modifier
            //.padding(10.dp)
            .fillMaxWidth()
            .height(30.dp).clickable { openWeatherFragment(city) } )
    }
    fun openWeatherFragment(city: String){
        val bundle =  bundleOf()
        bundle.putString(WeatherFragment.CITY_KEY,city)
        findNavController().navigate(R.id.action_citiesFragment_to_weatherFragment,bundle)
    }

}