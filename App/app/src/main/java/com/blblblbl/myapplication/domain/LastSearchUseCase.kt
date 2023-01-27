package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import javax.inject.Inject

class LastSearchUseCase @Inject constructor(
    private val persistentStorage: PersistentStorage
) {
    fun get(name:String):String?{
        return persistentStorage.getProperty(name)
    }
    fun set(name:String,value:String){
        persistentStorage.addProperty(name,value)
    }
}