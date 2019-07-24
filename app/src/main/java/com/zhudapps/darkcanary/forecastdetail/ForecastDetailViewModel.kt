package com.zhudapps.darkcanary.forecastdetail

import android.location.Address
import android.location.Geocoder
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
import java.io.IOException
import javax.inject.Inject

class ForecastDetailViewModel @Inject constructor(
    val repo: ForecastRepository,
    val connectivityManager: ConnectivityManager,
    val geocoder: Geocoder
) : ViewModel() {

    companion object {
        private const val TAG = "ForecastDetailViewModel"
    }

    val displayDate: MutableLiveData<String> = MutableLiveData()
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
                                    displayDate.value = repo.currentTimeMachinneForecast?.time?.let { DateTimeUtils.getDisplayDate(it) } ?: "No time indicated"
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

    fun location(): String? {
        val currentForecast = repo.currentTimeMachinneForecast
        var addresses: List<Address> = emptyList()

        if (currentForecast?.latitude != null && currentForecast.longitude != null) {
            try {
                addresses = geocoder.getFromLocation(
                    currentForecast.latitude.toDouble(),
                    currentForecast.longitude.toDouble(),
                    1)
            } catch (ioException: IOException) {
                // Catch network or other I/O problems.
               // errorMessage = getString(R.string.service_not_available)
                //Log.e(TAG, errorMessage, ioException)
            } catch (illegalArgumentException: IllegalArgumentException) {
                // Catch invalid latitude or longitude values.
                //errorMessage = getString(R.string.invalid_lat_long_used)
                //Log.e(TAG, "$errorMessage. Latitude = $location.latitude , " +
                       // "Longitude =  $location.longitude", illegalArgumentException)
            }
        }

        // Handle case where no address was found.
        return if (addresses.isEmpty()) {
//            if (errorMessage.isEmpty()) {
//                errorMessage = getString(R.string.no_address_found)
//                Log.e(TAG, errorMessage)
//            }
//            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage)
            null
        } else {
            val address = addresses[0]
            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            val addressFragments = with(address) {
                (0..maxAddressLineIndex).map { getAddressLine(it) }
            }
            addressFragments.joinToString(separator = "\n")
//            deliverResultToReceiver(Constants.SUCCESS_RESULT,
//                addressFragments.joinToString(separator = "\n"))
        }
    }
}
