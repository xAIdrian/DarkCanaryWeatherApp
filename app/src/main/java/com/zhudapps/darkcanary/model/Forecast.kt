package com.zhudapps.darkcanary.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.DAILY_ID
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.DAY_ID
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.FORECASTS

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Entity(
    tableName = FORECASTS,
    foreignKeys = [
        ForeignKey(
            entity = Daily::class,
            parentColumns = arrayOf(DAY_ID),
            childColumns = arrayOf(DAILY_ID),
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
data class Forecast(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int,
    @ColumnInfo(name = DAILY_ID)
    var dailyId: Int,

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
    var uvIndex: String
)