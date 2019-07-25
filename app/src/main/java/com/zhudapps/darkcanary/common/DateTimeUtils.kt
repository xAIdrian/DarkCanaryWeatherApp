package com.zhudapps.darkcanary.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateTimeUtils {

    fun getForecastDate(current: Long, dayOffset: Int): Long {
        return if (dayOffset > 0) {
            val getDays = dayOffset * 24 * 60 * 60 * 1000L
            (current + getDays) / 1000
        } else {
            current / 1000
        }
    }

    @SuppressLint("SimpleDateFormat") //todo get back to translating Timezone to Locale
    fun getDisplayDate(time: Long): String {
        val format = SimpleDateFormat("EEEE")
        val dateFormat = java.util.Date(time * 1000)
        return format.format(dateFormat)
    }
}