package com.zhudapps.darkcanary.main

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.hadilq.liveevent.LiveEvent
import javax.inject.Inject

/**
 * Created by adrian mohnacs on 2019-07-12
 */
class MainViewModel @Inject constructor(private val fusedLocationClient: FusedLocationProviderClient): ViewModel() {

    init {
        Log.e(TAG, "breaker startup")
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val eventStart = LiveEvent<Location>() //https://github.com/hadilq/LiveEvent/
    val lastKnownLocationLiveData: LiveData<Location> = eventStart

    @SuppressLint("MissingPermission") //supress permission check because we check in MainActivity (activity context is required)
    fun initUserLocation() {
        val defaultLocation = Location("").apply {
            latitude = 40.756757
            longitude = -73.976137
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            eventStart.value = it ?: defaultLocation
        }
    }
}