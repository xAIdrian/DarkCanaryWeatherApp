package com.zhudapps.darkcanary.model

/**
 * Created by adrian mohnacs on 2019-07-13
 */
//@Entity
data class Forecast (
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