package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.ForecastRepository
import com.example.example.ForecastResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCurrentWeatherUseCaseTest {
    @Test
    fun getCurrentWeatherTest(){
        runBlocking {
            val repository = mock<ForecastRepository>()
            val response = ForecastResponse()
            val loc = "loc"
            Mockito.`when`(repository.getCurrent(loc)).thenReturn(response)
            val useCase = GetCurrentWeatherUseCase(repository)
            val actual = useCase.getCurrentWeather(loc)
            Assert.assertEquals(response.hashCode(),actual.hashCode())
        }

    }
    @Test
    fun getSaved(){
        runBlocking {
            val repository = mock<ForecastRepository>()
            val response = ForecastResponse()
            Mockito.`when`(repository.getCurrentSaved()).thenReturn(response)
            val useCase = GetCurrentWeatherUseCase(repository)
            val actual = useCase.getSaved()
            Assert.assertEquals(response.hashCode(),actual.hashCode())
        }

    }
}