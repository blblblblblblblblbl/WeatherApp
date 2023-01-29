package com.blblblbl.myapplication.data.persistent_storage

interface PersistentStorage {
    fun addProperty(name: String?, value: String?)

    fun getProperty(name: String?): String?
}