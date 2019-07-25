package com.zhudapps.darkcanary.forecast

import android.location.Location
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import com.zhudapps.darkcanary.common.DateTimeUtils
import com.zhudapps.darkcanary.domain.ForecastRepository
import com.zhudapps.darkcanary.main.MainViewModel
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    companion object {
        private const val TAG = "ForecastViewModel"
    }

    init {
        Log.e(TAG, "hello from viewmodel")
    }

    internal var mainViewModel: MainViewModel? = null
        set(value) {
            field = value
            field?.let { setObservables(it) }
        }

    private var currentTimeMachineForecast: TimeMachineForecast? = null

    var forecastIndex: Int = -1
    private var userLocation: Location? = null

    val forecastLiveData = MutableLiveData<TimeMachineForecast>()

    private val eventStart = LiveEvent<String>() //https://github.com/hadilq/LiveEvent/
    val launchDetailsFragmentEvent: LiveData<String> = eventStart

    var readyForNextCall = false

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()

        super.onCleared()
    }

    private fun setObservables(mainViewModel: MainViewModel) {
        mainViewModel.lastKnownLocationLiveData.observeForever { location: Location? ->
            //this can also be done overriding Locations "equals" method
            if (userLocation?.latitude != location?.latitude
                || userLocation?.longitude != userLocation?.longitude) {

                userLocation = location

                getForecast(forecastIndex)
            }
        }
    }

    fun getForecast(offset: Int) {

        //if this is the first call to get the Forcast let it through
        //otherwise the user's location has changed
        //if (offset != forecastIndex || forecastIndex == 0 && readyForNextCall) { //daysOffset == 0 allowed to account for the initial call when we init location
            forecastIndex = offset
            val forecastDate = DateTimeUtils.getForecastDate(System.currentTimeMillis(), forecastIndex)
            getForecastWithLocation(forecastDate, userLocation)
        //}
    }

    private fun getForecastWithLocation(forecastDate: Long, userLocation: Location?) {

        if (userLocation != null) {

            try {
                val connection = isInternetConnected()
                compositeDisposable.add(
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
                                    forecastLiveData.value = it.apply {
                                        dayOfWeek = DateTimeUtils.getDisplayDate(forecastDate)
                                        time = forecastDate
                                        currentTimeMachineForecast = it
                                    }
                                }
                            }, {
                                //error state
                                Log.e(TAG, it.message ?: it.toString())
                            })
                )
            } catch (e: Exception) {
                Log.e(TAG, e.message ?: "e is null")
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

    fun launchDetailsFragment() {
        forecastRepository.currentTimeMachinneForecast = this.currentTimeMachineForecast
        eventStart.value = "getterString"
    }
}
