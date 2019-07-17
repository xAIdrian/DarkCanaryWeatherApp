package com.zhudapps.darkcanary.domain.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    suspend fun insertTimeMachineForecasts(timeMachineForecasts: List<TimeMachineForecast>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourly(hourly: Hourly)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaily(daily: Daily)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(vararg forecasts: List<Forecast>)

    @Query("SELECT * FROM time_machine_forecasts")
    suspend fun getTimeMachineForecasts(): ArrayList<TimeMachineForecast>


}