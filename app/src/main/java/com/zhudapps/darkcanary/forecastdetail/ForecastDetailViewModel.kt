package com.zhudapps.darkcanary.forecastdetail

import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhudapps.darkcanary.common.DateTimeUtils
import com.zhudapps.darkcanary.domain.ForecastRepository
import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ForecastDetailViewModel @Inject constructor(
    val repo: ForecastRepository,
    val connectivityManager: ConnectivityManager
) : ViewModel() {

    val forecastLiveData = MutableLiveData<TimeMachineForecast>()

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun getCurrentTimeMachineForcast() {
        //todo offline implementation
        val currentForecast = repo.currentTimeMachinneForecast
        if (currentForecast != null) {
            //todo conditions that indicate its "freshness"

            compositeDisposable.add(
                repo.fetchForecast(
                    currentForecast.latitude,
                    currentForecast.longitude,
                    currentForecast.time,
                    true,
                    currentForecast.id
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { forecast ->
                            forecast?.let {
                                forecastLiveData.value = it.apply {
                                    dayOfWeek = DateTimeUtils.getDisplayDate(currentForecast.time)
                                    time = currentForecast.time
                                }
                            }
                        }, {
                            //error state
                            Log.e("TAG for ForecastDetailViewModel", it.message ?: it.toString())
                        }
                    )
            )
        } else {
            throw Exception("Something has gone wrong and we have never set the current Forecast")

        }
    }
}
