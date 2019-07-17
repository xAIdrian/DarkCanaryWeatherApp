package com.zhudapps.darkcanary.domain

import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.Single

/**
 * Created by adrian mohnacs on 2019-07-13
 */
interface IForecastRepository {

    fun fetchForecast(latitude: Double, longitude: Double, time: Long): Single<TimeMachineForecast>
}