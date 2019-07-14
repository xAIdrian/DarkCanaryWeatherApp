package com.zhudapps.darkcanary.model

/**
 * Created by adrian mohnacs on 2019-07-13
 */
//@Entity(tableName = "time_machine_forecasts")
data class TimeMachineForecast(
  var offset: String,
  var timezone: String,
  var latitude: String,
  var longitude: String
//  @Embedded var daily: Daily,
//  @Embedded var hourly: Hourly,
//  @PrimaryKey
//  var uuid: UUID = UUID.randomUUID()
)