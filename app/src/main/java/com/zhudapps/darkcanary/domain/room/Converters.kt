package com.zhudapps.darkcanary.domain.room

import androidx.room.TypeConverter
import com.google.android.gms.awareness.state.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhudapps.darkcanary.model.Forecast
import com.zhudapps.darkcanary.model.TimeMachineForecast
import com.zhudapps.darkcanary.model.WeatherIcon


/**
 * Created by adrian mohnacs on 2019-07-16
 */
object Converters { //declaring as object ensures Singleton
    @TypeConverter
    @JvmStatic
    fun fromStringToForecastArrayList(stringValue: String): ArrayList<Forecast> {
        val listType = object : TypeToken<ArrayList<Forecast>>() { }.type
        return Gson().fromJson(stringValue, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromForecastArrayListToString(list: ArrayList<Forecast>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromStringToTimeMachineForecast(stringValue: String): TimeMachineForecast {
        val listType = object : TypeToken<TimeMachineForecast>() { }.type
        return Gson().fromJson(stringValue, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromTimeMachineForecastToString(list: TimeMachineForecast): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromStringToWeatherIcon(stringValue: String): WeatherIcon {
        return when(stringValue) {
            WeatherIcon.CLEAR_DAY.stringValue -> WeatherIcon.CLEAR_DAY
            WeatherIcon.CLEAR_NIGHT.stringValue -> WeatherIcon.CLEAR_NIGHT
            WeatherIcon.CLOUDY.stringValue -> WeatherIcon.CLOUDY
            WeatherIcon.FOG.stringValue -> WeatherIcon.FOG
            WeatherIcon.HAIL.stringValue -> WeatherIcon.HAIL
            WeatherIcon.PARTLY_CLOUDY_DAY.stringValue -> WeatherIcon.PARTLY_CLOUDY_DAY
            WeatherIcon.PARTLY_CLOUDY_NIGHT.stringValue -> WeatherIcon.PARTLY_CLOUDY_NIGHT
            WeatherIcon.RAIN.stringValue -> WeatherIcon.RAIN
            WeatherIcon.SLEET.stringValue -> WeatherIcon.SLEET
            WeatherIcon.SNOW.stringValue -> WeatherIcon.SNOW
            WeatherIcon.THUNDERSTORM.stringValue -> WeatherIcon.THUNDERSTORM
            WeatherIcon.WIND.stringValue -> WeatherIcon.WIND
            else -> WeatherIcon.TORNADO
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromWeatherIconToString(icon: WeatherIcon): String {
        return when (icon) {
            WeatherIcon.CLEAR_DAY -> WeatherIcon.CLEAR_DAY.stringValue
            WeatherIcon.CLEAR_NIGHT -> WeatherIcon.CLEAR_NIGHT.stringValue
            WeatherIcon.CLOUDY -> WeatherIcon.CLOUDY.stringValue
            WeatherIcon.FOG -> WeatherIcon.FOG.stringValue
            WeatherIcon.HAIL -> WeatherIcon.HAIL.stringValue
            WeatherIcon.PARTLY_CLOUDY_DAY -> WeatherIcon.PARTLY_CLOUDY_DAY.stringValue
            WeatherIcon.PARTLY_CLOUDY_NIGHT -> WeatherIcon.PARTLY_CLOUDY_NIGHT.stringValue
            WeatherIcon.RAIN -> WeatherIcon.RAIN.stringValue
            WeatherIcon.SLEET -> WeatherIcon.SLEET.stringValue
            WeatherIcon.SNOW -> WeatherIcon.SNOW.stringValue
            WeatherIcon.THUNDERSTORM -> WeatherIcon.THUNDERSTORM.stringValue
            WeatherIcon.WIND -> WeatherIcon.WIND.stringValue
            else -> WeatherIcon.TORNADO.stringValue
        }
    }
}