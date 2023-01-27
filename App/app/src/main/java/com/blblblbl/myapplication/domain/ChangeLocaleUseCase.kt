package com.blblblbl.myapplication.domain

import android.content.Context
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import dagger.hilt.android.qualifiers.ApplicationContext
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