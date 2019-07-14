package com.zhudapps.darkcanary.forecast

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhudapps.darkcanary.domain.DataManager
import com.zhudapps.darkcanary.main.MainViewModel
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val dataManager: DataManager) : ViewModel() {

    companion object {
        private const val TAG = "ForecastViewModel"
    }

    internal var mainViewModel: MainViewModel? = null
        set(value) {
            field = value
            field?.let { setObservables(it) }
        }
    val forcastLiveData = MutableLiveData<TimeMachineForecast>()

    private fun setObservables(mainViewModel: MainViewModel) {
        mainViewModel.lastKnownLocationLiveData.observeForever { location: Location? ->
            userLocation = location
            getForecast()
        }
    }

    private var userLocation: Location? = null

    private var isReadyForUpdate = true

    fun getForecast() {
        //if we don't have a location prompt the viewModel for it and say we're ready for an update
        if (isReadyForUpdate) {
            if (userLocation != null) {
                userLocation?.let {
                    dataManager.getTimeMachineForecast(
                        it.latitude,
                        it.longitude,
                        System.currentTimeMillis() / 1000
                    )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe (
                            { forecast ->
                                forecast?.let { it ->
                                    forcastLiveData.value = forecast
                                }
                            }, {
                                //error state
                                Log.e(TAG, it.message)
                            })
                }
                isReadyForUpdate = false
            } else {
                isReadyForUpdate = true
                mainViewModel?.initUserLocation()
            }
        }
    }
}
