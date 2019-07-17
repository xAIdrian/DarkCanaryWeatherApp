package com.zhudapps.darkcanary.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.DAILY_ID
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.FORECASTS
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.HOURLY_ID
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.ID
import java.util.*

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Entity(
    tableName = FORECASTS,
    foreignKeys = [
        ForeignKey(
            entity = Hourly::class,
            parentColumns = arrayOf(ID),
            childColumns = arrayOf(HOURLY_ID),
            onUpdate = CASCADE
        ),
        ForeignKey(
            entity = Daily::class,
            parentColumns = arrayOf(ID),
            childColumns = arrayOf(DAILY_ID),
            onUpdate = CASCADE
        )
    ]
)
data class Forecast(
    var summary: String,
    var precipProbability: String,
    var visibility: String,
    var precipIntensity: String,
    var icon: WeatherIcon,
    var cloudCover: String,
    var windBearing: String,
    var apparentTemperature: String,
    var apparentTemperatureHigh: String,
    var apparentTemperatureHighTime: String,
    var apparentTemperatureLow: String,
    var apparentTemperatureLowTime: String,
    var pressure: String,
    var dewPoint: String,
    var temperature: String,
    var humidity: String,
    var time: String,
    var windSpeed: String,
    var uvIndex: String,

    @PrimaryKey
    @NonNull
    var id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "hourly_id")
    var hourlyId: UUID = UUID(0L, 0L),
    @ColumnInfo(name = "daily_id")
    var dailyId: UUID = UUID(0L, 0L)
)