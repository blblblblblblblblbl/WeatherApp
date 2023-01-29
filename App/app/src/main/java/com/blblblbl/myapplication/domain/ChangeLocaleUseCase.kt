package com.blblblbl.myapplication.domain


import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import javax.inject.Inject


class ChangeLocaleUseCase @Inject constructor(
    private val persistentStorage: PersistentStorage
){
    //@Inject lateinit var persistentStorage: IPersistentStorage
    fun saveLocale(locale:String){
        persistentStorage.addProperty(PersistentStorageImpl.LANGUAGE_CODE,locale)
    }
    fun getLocale():String?{
        return persistentStorage.getProperty(PersistentStorageImpl.LANGUAGE_CODE)
    }
}