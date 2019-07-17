package com.zhudapps.darkcanary.forecast

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhudapps.darkcanary.domain.ForecastRepository
import com.zhudapps.darkcanary.main.MainViewModel
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val forecastRepository: ForecastRepository) : ViewModel() {

    companion object {
        private const val TAG = "ForecastViewModel"
    }

    internal var mainViewModel: MainViewModel? = null
        set(value) {
            field = value
            field?.let { setObservables(it) }
        }
    var daysToOffset: Int = -1

    val forcastLiveData = MutableLiveData<TimeMachineForecast>()

    private fun setObservables(mainViewModel: MainViewModel) {
        mainViewModel.lastKnownLocationLiveData.observeForever { location: Location? ->
            userLocation = location
            getForecast(daysToOffset)
        }
    }

    private var userLocation: Location? = null

    fun getForecast(offset: Int) {

        if (offset != daysToOffset || daysToOffset == 0) { //daysOffset == 0 allowed to account for the initial call when we init location
            daysToOffset = offset
            val forecastDate = getForecastDate(System.currentTimeMillis(), daysToOffset)
            getForecastWithLocation(forecastDate, userLocation)
        }
    }

    @SuppressLint("CheckResult")
    private fun getForecastWithLocation(forecastDate: Long, userLocation: Location?) {
        if (userLocation != null) {
            forecastRepository.fetchForecast(
                userLocation.latitude,
                userLocation.longitude,
                forecastDate
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { forecast ->
                        forecast?.let {
                            forcastLiveData.value = it
                        }
                    }, {
                        //error state
                        Log.e(TAG, it.message ?: it.toString())
                    })
        } else {
            mainViewModel?.initUserLocation()
        }
    }

    private fun getForecastDate(current: Long, dayOffset: Int): Long {
        return if (dayOffset > 0) {
            val getDays = dayOffset * 24 * 60 * 60 * 1000L
            (current + getDays) / 1000
        } else {
            current / 1000
        }
    }

    fun getDisplayDate(time: String): String {
        val sdf = SimpleDateFormat("EEEE")
        return sdf.format(
            Date(
                getForecastDate(time.toLong(), daysToOffset)
            )
        )
    }
}
