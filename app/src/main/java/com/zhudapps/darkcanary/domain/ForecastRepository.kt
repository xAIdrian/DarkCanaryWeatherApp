package com.zhudapps.darkcanary.domain

import com.zhudapps.darkcanary.domain.retrofit.DarkSkyEndpoint
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by adrian mohnacs on 2019-07-13
 */
class ForecastRepository @Inject constructor(
    private val forecastEndpoint: DarkSkyEndpoint
) : IForecastRepository {

    override fun fetchForecast(key: String, latitude: Double, longitude: Double, time: Long): Single<TimeMachineForecast> {
        return forecastEndpoint.getTimeMachineForecast(key, latitude, longitude, time)
    }

}