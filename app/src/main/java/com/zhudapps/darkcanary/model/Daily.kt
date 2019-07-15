package com.zhudapps.darkcanary.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Entity(
    tableName = "dailies",
    foreignKeys = [
        ForeignKey(
            entity = TimeMachineForecast::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("time_machine_forecast_id"),
            onDelete = CASCADE
        )
    ]
)
data class Daily(
    var data: ArrayList<Forecast>,

    @PrimaryKey
    @NonNull
    var id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "time_machine_forecast_id")
    val forecastId: UUID
)
