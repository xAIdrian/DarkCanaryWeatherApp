package com.zhudapps.darkcanary.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.DAILIES
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.ID
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.TIME_MACHINE_FORECAST_ID
import kotlin.collections.ArrayList

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Entity(
    tableName = DAILIES,
    foreignKeys = [
        ForeignKey(
            entity = TimeMachineForecast::class,
            parentColumns = arrayOf(ID),
            childColumns = arrayOf(TIME_MACHINE_FORECAST_ID),
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
data class Daily(
    @PrimaryKey(autoGenerate = true)
    var dayid: Int,
    @ColumnInfo(name = TIME_MACHINE_FORECAST_ID)
    var forecastId: Int,

    @SerializedName("data")
    var forecasts: ArrayList<Forecast>
)
