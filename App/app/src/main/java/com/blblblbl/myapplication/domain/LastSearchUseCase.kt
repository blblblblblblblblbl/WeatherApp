package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import javax.inject.Inject

class LastSearchUseCase @Inject constructor(
    private val persistentStorageImpl: PersistentStorageImpl
) {
    fun get(name:String):String?{
        return persistentStorageImpl.getProperty(name)
    }
    fun set(name:String,value:String){
        persistentStorageImpl.addProperty(name,value)
    }
}