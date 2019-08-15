package com.zhudapps.darkcanary.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.zhudapps.darkcanary.domain.room.ForecastDatabase.Companion.TIME_MACHINE_FORECASTS
import java.util.*

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Entity(tableName = TIME_MACHINE_FORECASTS)
data class TimeMachineForecast(
  @PrimaryKey(autoGenerate = true)
  @NonNull
  var id: Int,

  var offset: String,
  var timezone: String,
  var latitude: String,
  var longitude: String,
  @Ignore var time: Long,
  @Ignore var dayOfWeek: String,
  @Ignore var daily: Daily?,
  @Ignore var hourly: Hourly?
) {
  constructor() : this(
    -1,
    "",
    "",
    "",
    "",
    0,
    "",
    null,
    null
  )
}