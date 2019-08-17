package com.zhudapps.darkcanary.domain

import android.util.Log
import com.zhudapps.darkcanary.BuildConfig
import com.zhudapps.darkcanary.domain.retrofit.DarkSkyEndpoint
import com.zhudapps.darkcanary.domain.room.ForecastDao
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Singleton
class ForecastRepository @Inject constructor(
    private val forecastEndpoint: DarkSkyEndpoint,
    private val forecastDao: ForecastDao
) : IForecastRepository {

    init {
        Log.e("ForecastRepo", "init forecast repo")
    }

    override fun fetchForecast(
        latitude: String,
        longitude: String,
        time: Long,
        isConnectedToInternet: Boolean,
        id: Int
    ): Single<TimeMachineForecast> {

        return fetchForecast(
            latitude.toDouble(),
            latitude.toDouble(),
            time,
            isConnectedToInternet,
            id
        )
    }

    var currentTimeMachinneForecast: TimeMachineForecast? = null

    override fun fetchForecast(
        latitude: Double,
        longitude: Double,
        time: Long,
        isConnectedToInternet: Boolean,
        id: Int
    ): Single<TimeMachineForecast> {
        return if (isConnectedToInternet) {
            return forecastEndpoint.getTimeMachineForecast(BuildConfig.apikey, latitude, longitude, time)
                .doAfterSuccess {
                    it.id = id
                    it.daily?.dayid = id
                    it.hourly?.hourid = id

                    it.daily?.timeMachineForecastId = id
                    it.hourly?.timeMachineForecastId = id

                    GlobalScope.launch(Dispatchers.IO) {
                        forecastDao.insertTimeMachineForecasts(it)
                    }
                }
        } else {
            forecastDao.getTimeMachineForecasts(id)
        }
    }
}