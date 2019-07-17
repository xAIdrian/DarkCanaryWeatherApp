package com.zhudapps.darkcanary.domain.room

import androidx.room.*
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.ID
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.TIME_MACHINE_FORECASTS
import com.zhudapps.darkcanary.model.Daily
import com.zhudapps.darkcanary.model.Forecast
import com.zhudapps.darkcanary.model.Hourly
import com.zhudapps.darkcanary.model.TimeMachineForecast

/**
 * Created by adrian mohnacs on 2019-07-14
 */
@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeMachineForecasts(timeMachineForecasts: TimeMachineForecast)

    @Query("SELECT * FROM $TIME_MACHINE_FORECASTS WHERE $ID = :forecastId")
    suspend fun getTimeMachineForecasts(forecastId: String): TimeMachineForecast

    @Query("DELETE FROM $TIME_MACHINE_FORECASTS")
    suspend fun clearDatabase()
}