package com.zhudapps.darkcanary.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zhudapps.darkcanary.model.Daily
import com.zhudapps.darkcanary.model.Forecast
import com.zhudapps.darkcanary.model.TimeMachineForecast

/**
 * Created by adrian mohnacs on 2019-07-14
 */
@Database(entities = [TimeMachineForecast::class, Daily::class, Forecast::class], version = 1)
@TypeConverters(Converters::class)
abstract class ForecastDatabase: RoomDatabase() {

    companion object {

        const val TIME_MACHINE_FORECASTS = "time_machine_forecasts"
        const val HOURLIES = "hourlies"
        const val DAILIES = "dailies"
        const val FORECASTS = "forecasts"

        const val ID = "id"
        const val TIME_MACHINE_FORECAST_ID = "time_machine_forecast_id"
        const val DAY_ID = "dayid"
        const val DAILY_ID = "daily_id"
    }

    abstract fun forecastDao(): ForecastDao
}