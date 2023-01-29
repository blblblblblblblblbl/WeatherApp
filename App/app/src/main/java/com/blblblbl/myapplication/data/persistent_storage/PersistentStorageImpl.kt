package com.blblblbl.myapplication.data.persistent_storage

import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistentStorageImpl @Inject constructor(
    @ApplicationContext context: Context
):PersistentStorage {

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var context: Context = context

    private fun init() {
        sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    override fun addProperty(name: String?, value: String?) {
        if (sharedPreferences == null) {
            init()
        }
        editor!!.putString(name, value)
        editor!!.apply()
    }

    override fun getProperty(name: String?): String? {
        if (sharedPreferences == null) {
            init()
        }
        return sharedPreferences!!.getString(name, null)
    }
    companion object{
        const val STORAGE_NAME = "StorageName"
        const val LAST_SEARCH = "lastsearch"
        const val LANGUAGE_CODE = "LANGUAGE_CODE"
        const val CURRENT_WEATHER = "CURRENT_WEATHER"
    }
}
@Module
@InstallIn(SingletonComponent::class)
abstract class PersistentStorageModule{
    @Binds
    abstract fun pers(persistentStorageImpl: PersistentStorageImpl):PersistentStorage
}