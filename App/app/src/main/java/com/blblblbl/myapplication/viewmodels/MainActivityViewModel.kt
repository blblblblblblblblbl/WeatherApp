package com.blblblbl.myapplication.viewmodels

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import com.blblblbl.myapplication.domain.ChangeLocaleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val changeLocaleUseCase: ChangeLocaleUseCase
):ViewModel() {
    fun setSavedLocale(context: Context){
        getLocale()?.let { language->
            val locale = Locale(language)
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            context.resources.updateConfiguration(configuration, null)
        }
    }
    fun getLocale():String?{
        return changeLocaleUseCase.getLocale()
    }
}