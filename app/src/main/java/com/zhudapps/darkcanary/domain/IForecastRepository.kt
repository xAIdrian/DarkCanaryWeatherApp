package com.zhudapps.darkcanary.domain

import com.zhudapps.darkcanary.model.TimeMachineForecast
import retrofit2.Response

/**
 * Created by adrian mohnacs on 2019-07-13
 */
interface IForecastRepository {

    suspend fun fetchForecast(latitude: Double, longitude: Double, time: Long, isConnectedToInternet: Boolean): TimeMachineForecast?
}