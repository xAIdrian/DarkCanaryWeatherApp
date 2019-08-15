package com.zhudapps.darkcanary.main

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    var killLooper = 0

    @SuppressLint("MissingPermission") //supress permission check because we check in MainActivity (activity context is required)
    fun initUserLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            // Got last known location. In some rare situations this can be null.
            //if we are in a situration where location is null our observer pattern will cause a loop. so this counter deals with that
            if (killLooper <= 7) {
                killLooper++
                Log.e(TAG, location.toString())
                eventStart.value = location
            }
        }
    }
}