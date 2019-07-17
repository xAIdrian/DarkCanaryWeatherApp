package com.zhudapps.darkcanary.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.DAILIES
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.ID
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.TIME_MACHINE_FORECAST_ID
import java.util.*
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
            onUpdate = CASCADE
        )
    ]
)
data class Daily(
    var data: ArrayList<Forecast>,

    @PrimaryKey
    @NonNull
    var id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = TIME_MACHINE_FORECAST_ID)
    val forecastId: UUID = UUID(0L, 0L)
)
