package com.zhudapps.darkcanary.model

import com.google.gson.annotations.SerializedName
import com.zhudapps.darkcanary.R

/**
 * Created by adrian mohnacs on 2019-07-14
 */
enum class WeatherIcon(val value: Int) {
    @SerializedName("clear-day")
    CLEAR_DAY(R.drawable.ic_wi_day_sunny_100),
    @SerializedName("clear-night")
    CLEAR_NIGHT(R.drawable.ic_wi_night_clear_100),
    @SerializedName("rain")
    RAIN(R.drawable.ic_wi_rain_100),
    @SerializedName("snow")
    SNOW(R.drawable.ic_wi_snow_100),
    @SerializedName("sleet")
    SLEET(R.drawable.ic_wi_sleet_100),
    @SerializedName("wind")
    WIND(R.drawable.ic_wi_cloudy_gusts_100),
    @SerializedName("fog")
    FOG(R.drawable.ic_wi_fog_100),
    @SerializedName("cloudy")
    CLOUDY(R.drawable.ic_wi_cloudy_100),
    @SerializedName("partly-cloudy-day")
    PARTLY_CLOUDY_DAY(R.drawable.ic_wi_day_cloudy_100),
    @SerializedName("partly-cloudy-night")
    PARTLY_CLOUDY_NIGHT(R.drawable.ic_wi_night_alt_cloudy_100),
    @SerializedName("hail")
    HAIL(R.drawable.ic_wi_hail_100),
    @SerializedName("thunderstorm")
    THUNDERSTORM(R.drawable.ic_wi_thunderstorm_100),
    @SerializedName("tornado")
    TORNADO(R.drawable.ic_wi_tornado_100)
}