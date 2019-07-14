package com.zhudapps.darkcanary.domain

import com.zhudapps.darkcanary.BuildConfig
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by adrian mohnacs on 2019-07-13
 */
class DataManager @Inject constructor(
    private val forecastRepository: ForecastRepository
): IDataManager {

    companion object {
        private const val TAG = "DataManager"
    }

    override fun getTimeMachineForecast(latitude: Double, longitude: Double, time: Long): Single<TimeMachineForecast> {
        return forecastRepository.fetchForecast(BuildConfig.apikey, latitude, longitude, time).doAfterSuccess{
            //todo add to database
        }
    }

}