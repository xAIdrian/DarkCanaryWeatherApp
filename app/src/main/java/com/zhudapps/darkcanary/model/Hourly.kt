package com.zhudapps.darkcanary.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.HOURLIES
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.ID
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.TIME_MACHINE_FORECAST_ID
import kotlin.collections.ArrayList

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Entity(
    tableName = HOURLIES,
    foreignKeys = [
        ForeignKey(
            entity = TimeMachineForecast::class,
            parentColumns = arrayOf(ID),
            childColumns = arrayOf(TIME_MACHINE_FORECAST_ID),
            onUpdate = CASCADE
        )
    ]
)
data class Hourly(
    var summary: String,
    var data: ArrayList<Forecast>,
    var icon: String,

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var hourId: Int,
    @ColumnInfo(name = TIME_MACHINE_FORECAST_ID)
    var timeMachineForecastId: Int
)
