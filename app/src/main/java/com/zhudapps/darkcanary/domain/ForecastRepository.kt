package com.zhudapps.darkcanary.domain

import com.zhudapps.darkcanary.BuildConfig
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

    override fun fetchForecast(latitude: Double, longitude: Double, time: Long): Single<TimeMachineForecast> {
        return forecastEndpoint.getTimeMachineForecast(BuildConfig.apikey, latitude, longitude, time)
    }

}