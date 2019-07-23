package com.zhudapps.darkcanary.domain

import android.util.Log
import com.zhudapps.darkcanary.BuildConfig
import com.zhudapps.darkcanary.domain.retrofit.DarkSkyEndpoint
import com.zhudapps.darkcanary.domain.room.ForecastDao
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
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
                    it.daily.dayid = id
                    it.daily.forecastId = id

                    GlobalScope.launch(Dispatchers.IO) {
                        forecastDao.insertTimeMachineForecasts(it)
                    }
                }
        } else {
            forecastDao.getTimeMachineForecasts(id)
        }
    }
}