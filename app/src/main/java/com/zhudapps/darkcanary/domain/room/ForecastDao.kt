package com.zhudapps.darkcanary.domain.room

import androidx.room.*
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.ID
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.TIME_MACHINE_FORECASTS
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.Single

/**
 * Created by adrian mohnacs on 2019-07-14
 */
@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeMachineForecasts(timeMachineForecasts: TimeMachineForecast)

    @Query("SELECT * FROM $TIME_MACHINE_FORECASTS WHERE $ID = :id")
    fun getTimeMachineForecasts(id: Int): Single<TimeMachineForecast>

    @Query("DELETE FROM $TIME_MACHINE_FORECASTS")
    fun clearDatabase()
}