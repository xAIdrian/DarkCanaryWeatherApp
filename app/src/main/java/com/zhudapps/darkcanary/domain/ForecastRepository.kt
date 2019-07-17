package com.zhudapps.darkcanary.domain

import com.zhudapps.darkcanary.BuildConfig
import com.zhudapps.darkcanary.domain.retrofit.DarkSkyEndpoint
import com.zhudapps.darkcanary.domain.room.ForecastDao
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.Single
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*
import javax.inject.Inject

/**
 * Created by adrian mohnacs on 2019-07-13
 */
class ForecastRepository @Inject constructor(
    private val forecastEndpoint: DarkSkyEndpoint,
    private val forecastDao: ForecastDao
) : IForecastRepository {

    override suspend fun fetchForecast(
        latitude: Double,
        longitude: Double,
        time: Long,
        isConnectedToInternet: Boolean
    ): TimeMachineForecast? {
        return if (isConnectedToInternet) {
            return forecastEndpoint.getTimeMachineForecast(BuildConfig.apikey, latitude, longitude, time).apply {
                val response = this.body()

                response?.id = time.toString()
                response?.let {
                    GlobalScope.launch {
                        forecastDao.insertTimeMachineForecasts(it)
                    }
                }
            }.body()
        } else {
            forecastDao.getTimeMachineForecasts(time.toString())
        }
    }
}