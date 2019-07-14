package com.zhudapps.darkcanary.model

/**
 * Created by adrian mohnacs on 2019-07-13
 */
data class TimeMachineForecast(
  var offset: String,
  var timezone: String,
  var latitude: String,
  var daily: Daily,
  var hourly: Hourly,
  var longitude: String
)