package com.blblblbl.myapplication.domain

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import com.blblblbl.myapplication.data.PersistentStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject


class ChangeLocaleUseCase @Inject constructor(
    private val persistentStorage: PersistentStorage,
    @ApplicationContext private val context: Context
){

    fun saveLocale(locale:String){
        persistentStorage.addProperty(PersistentStorage.LANGUAGE_CODE,locale)
    }
    fun getLocale():String?{
        return persistentStorage.getProperty(PersistentStorage.LANGUAGE_CODE)
    }
}