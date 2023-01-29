package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.db.DBForecast
import com.blblblbl.myapplication.data.ForecastRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetForecastSavedListUseCaseTest {
    @Test
    fun executeTest(){
        runBlocking {
            val repository = mock<ForecastRepository>()
            val response = listOf<DBForecast>()
            Mockito.`when`(repository.getForecastsSavedList()).thenReturn(response)
            val useCase = GetForecastSavedListUseCase(repository)
            val actual = useCase.execute()
            Assert.assertEquals(response.hashCode(),actual.hashCode())
        }
    }
    @Test
    fun executeCityTest(){
        runBlocking {
            val repository = mock<ForecastRepository>()
            val response = listOf<String>()
            Mockito.`when`(repository.getForecastsCitiesSavedList()).thenReturn(response)
            val useCase = GetForecastSavedListUseCase(repository)
            val actual = useCase.executeCity()
            Assert.assertEquals(response.hashCode(),actual.hashCode())
        }
    }
}