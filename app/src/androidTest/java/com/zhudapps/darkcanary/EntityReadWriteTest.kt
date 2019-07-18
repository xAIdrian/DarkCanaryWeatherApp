package com.zhudapps.darkcanary

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zhudapps.darkcanary.domain.room.ForecastDao
import com.zhudapps.darkcanary.domain.room.ForecastDatabase
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EntityReadWriteTest {
    private lateinit var forecastDao: ForecastDao
    private lateinit var database: ForecastDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            ForecastDatabase::class.java //todo 2019-07-16 this needs to be changed to an in memory test database to make it more hermetic
        ).build()
        forecastDao = database.forecastDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    fun writeAndReadTimeMachineForecastInList() {
        val testForecasts = TestUtil.createTimeMachineForecasts(0)

        runBlocking {
            forecastDao.insertTimeMachineForecasts(testForecasts)
        }

        val timeMachines = runBlocking {
            forecastDao.getTimeMachineForecasts(0)
        }
        assertTrue(timeMachines != null)
    }

    @Test
    fun clearTimeMachineForecasts() {
        val testForecasts = TestUtil.createTimeMachineForecasts(0)
        runBlocking {
            forecastDao.insertTimeMachineForecasts(testForecasts)
        }

        runBlocking {
            forecastDao.clearDatabase()
        }

        val timeMachines = runBlocking {
            forecastDao.getTimeMachineForecasts(0)
        }
        assertNull(timeMachines)
    }
}