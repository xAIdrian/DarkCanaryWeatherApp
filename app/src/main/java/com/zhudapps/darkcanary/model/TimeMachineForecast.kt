package com.zhudapps.darkcanary.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Entity(tableName = "time_machine_forecasts")
data class TimeMachineForecast(
  var offset: String,
  var timezone: String,
  var latitude: String,
  var longitude: String,
  var daily: Daily,
  var hourly: Hourly,

  @PrimaryKey
  @NonNull
  var id: UUID = UUID.randomUUID()
)