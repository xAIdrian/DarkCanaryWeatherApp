package com.zhudapps.darkcanary.forecast

import android.annotation.SuppressLint
import android.location.Location
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhudapps.darkcanary.domain.ForecastRepository
import com.zhudapps.darkcanary.main.MainViewModel
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    companion object {
        private const val TAG = "ForecastViewModel"
    }

    internal var mainViewModel: MainViewModel? = null
        set(value) {
            field = value
            field?.let { setObservables(it) }
        }
    var forecastIndex: Int = -1

    val forecastLiveData = MutableLiveData<TimeMachineForecast>()

    private fun setObservables(mainViewModel: MainViewModel) {
        mainViewModel.lastKnownLocationLiveData.observeForever { location: Location? ->
            userLocation = location
            getForecast(forecastIndex)
        }
    }

    private var userLocation: Location? = null

    fun getForecast(offset: Int) {

        if (offset != forecastIndex || forecastIndex == 0) { //daysOffset == 0 allowed to account for the initial call when we init location
            forecastIndex = offset
            val forecastDate = getForecastDate(System.currentTimeMillis(), forecastIndex)
            getForecastWithLocation(forecastDate, userLocation)
        }
    }

    @SuppressLint("CheckResult")
    private fun getForecastWithLocation(forecastDate: Long, userLocation: Location?) {
        if (userLocation != null) {

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val connection = isInternetConnected()
                    forecastRepository.fetchForecast(
                        userLocation.latitude,
                        userLocation.longitude,
                        forecastDate,
                        connection,
                        forecastIndex
                    ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { forecast ->
                                forecast?.let {
                                    forecastLiveData.value = it
                                }
                            }, {
                                //error state
                                Log.e(TAG, it.message ?: it.toString())
                            })

                } catch (e: Exception) {
                    Log.e(TAG, e.message ?: "e is null")
                }
            }
        } else {
            mainViewModel?.initUserLocation()
        }
    }

    private fun isInternetConnected(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null
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
                getForecastDate(time.toLong(), forecastIndex)
            )
        )
    }
}
