package com.blblblbl.myapplication.data

import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import com.blblblbl.myapplication.data.persistent_storage.utils.StorageConverter

import com.example.example.ForecastResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ForecastRepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val databaseRepository: DatabaseRepository,
    private val persistentStorageImpl: PersistentStorageImpl
):ForecastRepository {

    override suspend fun getForecast(city:String, days: Int):DBForecast? {
        try {
            val forecastResponse:ForecastResponse = apiRepository.getForecast(city,days)
            forecastResponse?.let {
                val dbForecast = DBForecast(forecastResponse.location!!.name!!,
                    forecastResponse.location!!.localtime!!,
                    forecastResponse.forecast!!
                )
                databaseRepository.db.forecastDao().insertNDelete(dbForecast)
                return dbForecast
            }
        }
        catch (e:Throwable){}
        val forecastFromDB = getForecastSaved(city)
        return forecastFromDB?:null
    }
    override suspend fun getCurrent(loc:String):ForecastResponse{
        return apiRepository.getCurrent(loc)
    }
    override suspend fun getCurrentSaved():ForecastResponse?{
        val json = persistentStorageImpl.getProperty(PersistentStorageImpl.CURRENT_WEATHER)
        json?.let {json->
            val forecast = StorageConverter.forecastInfoFromJson(json)
            return forecast
        }
        return null
    }
    override suspend fun saveCurrent(forecastResponse: ForecastResponse){
        val json = StorageConverter.forecastInfoToJson(forecastResponse)
        persistentStorageImpl.addProperty(PersistentStorageImpl.CURRENT_WEATHER,json)
    }
    override fun getForecastsSavedList(): List<DBForecast> {

        return databaseRepository.db.forecastDao().getAll()
    }
    override fun getForecastsCitiesSavedList(): List<String> {

        return databaseRepository.db.forecastDao().getAllCities()
    }

    override suspend fun getForecastSaved(city: String): DBForecast {
        return databaseRepository.db.forecastDao().getForecast(city)
    }
}
@Module
@InstallIn(SingletonComponent::class)
abstract class ForecastRepositoryModule{
    @Binds
    abstract fun bindForecastRepository(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository
}
