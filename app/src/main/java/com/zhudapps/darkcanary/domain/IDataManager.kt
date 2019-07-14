package com.zhudapps.darkcanary.domain

import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.Single

interface IDataManager {
    fun getTimeMachineForecast(latitude: Double, longitude: Double, time: Long): Single<TimeMachineForecast>
}
