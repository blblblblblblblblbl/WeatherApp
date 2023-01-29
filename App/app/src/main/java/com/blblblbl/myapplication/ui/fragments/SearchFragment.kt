package com.blblblbl.myapplication.ui.fragments

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.WeatherWidget
import com.blblblbl.myapplication.data.db.DBForecast
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import com.blblblbl.myapplication.ui.compose.theming.CustomTheme
import com.blblblbl.myapplication.viewmodels.SearchViewModel
import com.murgupluoglu.flagkit.FlagKit
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getCurrentSaved()
        return ComposeView(requireContext()).apply {
            setContent {
                CustomTheme {
                    viewModel.ph.handlePermission()
                    var textHint:String =viewModel.getLastSearch(PersistentStorageImpl.LAST_SEARCH)?:""
                    Column {
                        var text by remember { mutableStateOf("") }
                        val trailingIconView = @Composable {
                            IconButton(
                                onClick = {
                                    if (text!="") {
                                        viewModel.setLastSearch(PersistentStorageImpl.LAST_SEARCH,text)
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
                        val weather = viewModel.weather.collectAsState()
                        lifecycleScope.launchWhenCreated { viewModel.location.collectLatest {
                            viewModel.getCurrentWeather()
                            val intent = Intent(requireActivity(), WeatherWidget::class.java)
                            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                            val ids: IntArray = AppWidgetManager
                                .getInstance(requireActivity().application)
                                .getAppWidgetIds(ComponentName(requireActivity().application, WeatherWidget::class.java))
                            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
                            requireActivity().sendBroadcast(intent)
                        }}
                        IconButton(onClick = { viewModel.getLocation(requireContext()) }) {
                            Icon(Icons.Default.Refresh, contentDescription = "refresh")
                        }
                        weather.value?.location?.name?.let { name->
                            Text(text = name, modifier = Modifier.align(CenterHorizontally), style = MaterialTheme.typography.headlineLarge)
                        }
                        weather.value?.current?.tempC?.let { temp->
                            Text(text = "$temp°C",
                                modifier = Modifier.align(CenterHorizontally),
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = 50.sp)
                        }
                        weather.value?.current?.condition?.text?.let{condition->
                            Text(text = condition,modifier = Modifier.align(CenterHorizontally), style = MaterialTheme.typography.bodyLarge)
                        }
                        weather.value?.current?.condition?.icon?.let{conditionIcon->
                            GlideImage(modifier = Modifier
                                .align(CenterHorizontally)
                                .size(64.dp),imageModel = {"https:$conditionIcon"})
                        }
                        weather.value?.current?.lastUpdated?.let{lastUpdated->
                            Text(text = "${stringResource(id = R.string.last_updated)}: $lastUpdated",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.align(CenterHorizontally) )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row() {
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                viewModel.setLocale(requireActivity(), RUSSIAN_LOCALE_CODE)}) {
                                Image(painter = painterResource(FlagKit.getResId(context, "ru")),
                                    contentDescription = "ru flag" )
                            }
                            IconButton(onClick = {
                                viewModel.setLocale(requireActivity(), ENGLISH_LOCALE_CODE) }) {
                                Image(painter = painterResource(FlagKit.getResId(context, "gb")),
                                    contentDescription = "eng flag" )
                            }
                        }
                        
                    }
                }
            }
        }
    }
    @Preview
    @Composable
    fun locationIcon(){
        Icon(Icons.Default.MyLocation, contentDescription = "location")

    }
    private fun search(city:String, days:Int){
        val dbForecast: DBForecast?
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
    companion object{
        const val RUSSIAN_LOCALE_CODE = "ru"
        const val ENGLISH_LOCALE_CODE = "en"
    }
}