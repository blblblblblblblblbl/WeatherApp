package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock

class ChangeLocaleUseCaseTest {
    @Test
    fun saveLocaleTest() {
        val persistentStorage = mock<PersistentStorage>()
        val locale = "locale"
        val useCase = ChangeLocaleUseCase(persistentStorage)
        val keyCaptor = argumentCaptor<String>()
        val valueCaptor = argumentCaptor<String>()
        useCase.saveLocale(locale)
        Mockito.verify(persistentStorage).addProperty(keyCaptor.capture(),valueCaptor.capture())
        Assert.assertEquals(PersistentStorageImpl.LANGUAGE_CODE, keyCaptor.firstValue)
        Assert.assertEquals(locale, valueCaptor.firstValue)
    }
    @Test
    fun getLocaleTest() {
        val persistentStorage = mock<PersistentStorage>()
        val locale = "locale"
        val useCase = ChangeLocaleUseCase(persistentStorage)
        Mockito.`when`(persistentStorage.getProperty(PersistentStorageImpl.LANGUAGE_CODE)).thenReturn("locale")
        val actual =useCase.getLocale()
        Assert.assertEquals(locale, actual)
    }
}