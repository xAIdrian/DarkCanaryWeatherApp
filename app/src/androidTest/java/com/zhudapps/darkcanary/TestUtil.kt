package com.zhudapps.darkcanary

import com.zhudapps.darkcanary.model.*

object TestUtil {

    val testDailyForecast = Daily(
        "0",
        "1",
        arrayListOf(
            Forecast(
                "0",
                "1",
                "summary0",
                "precipProb0",
                "visibility0",
                "precipIntent0",
                WeatherIcon.CLEAR_DAY,
                "cloudCover0",
                "windBearing0",
                "appTemp0",
                "appTempHigh0",
                "appTempHighTime0",
                "appTempLow0",
                "appTempLowTime0",
                "pressure0",
                "dewPoint0",
                "temp0",
                "humidity0",
                "time0",
                "windSpeed0",
                "uvIndex0"
            )
        )
    )

    fun createTimeMachineForecasts(i: Int): TimeMachineForecast {
        return TimeMachineForecast(
            "0",
                "testOffset$i",
                "testTimeZone$i",
                "$i",
                "${i + 1}",
                Daily(
                    "$i",
                    "${i + 1}",
                    arrayListOf(
                        Forecast(
                            "0",
                            "1",
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
                )
            )
        }
}