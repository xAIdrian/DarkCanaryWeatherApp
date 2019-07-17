package com.zhudapps.darkcanary

import com.zhudapps.darkcanary.model.*

object TestUtil {

    fun createTimeMachineForecasts(count: Int): List<TimeMachineForecast> {
        val timeMachines = ArrayList<TimeMachineForecast>()

        for (i in 0 until count) {
            val machine = TimeMachineForecast(
                "testOffset$i",
                "testTimeZone$i",
                "$i",
                "$i",
                Daily(
                    arrayListOf(
                        Forecast(
                            "summary$i",
                            "precipProb$i",
                            "visibility$i",
                            "precipIntent$i",
                            WeatherIcon.CLEAR_DAY,
                            "cloudCover$i",
                            "windBearing$i",
                            "appTemp$i",
                            "appTempHigh$i",
                            "appTempHighTime$i",
                            "appTempLow$i",
                            "appTempLowTime$i",
                            "pressure$i",
                            "dewPoint$i",
                            "temp$i",
                            "humidity$i",
                            "time$i",
                            "windSpeed$i",
                            "uvIndex$i"
                        )
                    )
                ),
                Hourly(
                    "summary$i",
                    arrayListOf(
                        Forecast(
                            "summary$i",
                            "precipProb$i",
                            "visibility$i",
                            "precipIntent$i",
                            WeatherIcon.CLEAR_NIGHT,
                            "cloudCover$i",
                            "windBearing$i",
                            "appTemp$i",
                            "appTempHigh$i",
                            "appTempHighTime$i",
                            "appTempLow$i",
                            "appTempLowTime$i",
                            "pressure$i",
                            "dewPoint$i",
                            "temp$i",
                            "humidity$i",
                            "time$i",
                            "windSpeed$i",
                            "uvIndex$i"
                        )
                    ),
                    "icon$i"
                )
            )
            timeMachines.add(machine)
        }
        return timeMachines
    }
}