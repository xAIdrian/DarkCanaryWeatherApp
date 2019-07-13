package com.zhudapps.darkcanary.main

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject

/**
 * Created by adrian mohnacs on 2019-07-12
 */ //don't forget to add DataManager as parameter here?
class MainViewModel @Inject constructor(private val fusedLocationClient: FusedLocationProviderClient): ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    //val lastKnowLocationLiveData = MutableLiveData<Location>()

    @SuppressLint("MissingPermission") //supress permission check because we check in MainActivity (activity context is required)
    fun initUserLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            // Got last known location. In some rare situations this can be null.
            Log.e(TAG, location.toString())

            /*
            Location is turned off in the device settings. The result could be null even if the last location was previously
            retrieved because disabling location also clears the cache.

            The device never recorded its location, which could be the case of a new device or a device that has
            been restored to factory settings.

            Google Play services on the device has restarted, and there is no active Fused Location Provider client
            that has requested location after the services restarted. To avoid this situation you can create a new client
            and request location updates yourself. For more information, see Receiving Location Updates.
             */
        }
    }
}