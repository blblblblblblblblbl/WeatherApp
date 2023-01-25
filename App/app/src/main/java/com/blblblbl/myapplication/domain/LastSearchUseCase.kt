package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.PersistantStorage
import javax.inject.Inject

class LastSearchUseCase @Inject constructor(
    private val persistantStorage: PersistantStorage
) {
    fun get(name:String):String?{
        return persistantStorage.getProperty(name)
    }
    fun set(name:String,value:String){
        persistantStorage.addProperty(name,value)
    }
}