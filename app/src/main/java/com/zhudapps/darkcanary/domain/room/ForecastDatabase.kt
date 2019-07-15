package com.zhudapps.darkcanary.domain.room

import androidx.room.Database
import com.zhudapps.darkcanary.model.Daily
import com.zhudapps.darkcanary.model.Forecast
import com.zhudapps.darkcanary.model.Hourly
import com.zhudapps.darkcanary.model.TimeMachineForecast

/**
 * Created by adrian mohnacs on 2019-07-14
 */
@Database(entities = [TimeMachineForecast::class, Hourly::class, Daily::class, Forecast::class], version = 1)
abstract class ForecastDatabase {
    abstract fun forecastDao(): ForecastDao
}