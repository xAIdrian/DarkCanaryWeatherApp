package com.zhudapps.darkcanary.domain

import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.Single

/**
 * Created by adrian mohnacs on 2019-07-13
 */
interface IForecastRepository {

    fun fetchForecast(
        latitude: String,
        longitude: String,
        time: Long,
        isConnectedToInternet: Boolean,
        id: Int
    ): Single<TimeMachineForecast>

    fun fetchForecast(
        latitude: Double,
        longitude: Double,
        time: Long,
        isConnectedToInternet: Boolean,
        id: Int
    ): Single<TimeMachineForecast>
}