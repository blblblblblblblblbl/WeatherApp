package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.DBForecast
import com.blblblbl.myapplication.data.ForecastRepository
import com.example.example.Forecast
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetForecastSavedUseCaseTest {
    @Test
    fun executeTest(){
        runBlocking {
            val repository = mock<ForecastRepository>()
            val useCase = GetForecastSavedUseCase(repository)
            val response = DBForecast("aaa","aaa", Forecast())
            val city = "aaa"
            Mockito.`when`(repository.getForecastSaved(city)).thenReturn(response)
            val actual = useCase.execute(city)
            Assert.assertEquals(response,actual)
        }
    }
}