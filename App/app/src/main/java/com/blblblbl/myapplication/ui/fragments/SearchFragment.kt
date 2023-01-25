package com.blblblbl.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.data.PersistantStorage
import com.blblblbl.myapplication.ui.compose.theming.CustomTheme
import com.blblblbl.myapplication.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                CustomTheme {
                    var textHint:String =viewModel.getLastSearch(PersistantStorage.LAST_SEARCH)?:""
                    Column {
                        var text by remember { mutableStateOf("") }
                        val trailingIconView = @Composable {
                            IconButton(
                                onClick = {
                                    if (text!="") {
                                        viewModel.setLastSearch(PersistantStorage.LAST_SEARCH,text)
                                        search(text, 7)
                                    }
                                    else if (text=="" && textHint!=""){
                                        search(textHint, 7)
                                    }
                                },
                            ) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "",
                                )
                            }
                        }
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text(text = textHint) },
                            placeholder = { Text(text = textHint) },
                            trailingIcon = if (text.isNotBlank()||textHint.isNotBlank()) trailingIconView else null,
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.large
                        )
                    }
                }
            }
        }
    }
    private fun search(city:String, days:Int){
        val dbForecast:DBForecast?
        runBlocking {
            dbForecast =viewModel.getForecast(city, days)
        }
        if (dbForecast==null){
            Toast.makeText(requireContext(),"no internet connection and saved forecast",Toast.LENGTH_LONG).show()
            return
        }
        val bundle =  bundleOf()
        bundle.putString(WeatherFragment.CITY_KEY,dbForecast.city)
        findNavController().navigate(R.id.action_searchFragment_to_weatherFragment,bundle)
    }

}