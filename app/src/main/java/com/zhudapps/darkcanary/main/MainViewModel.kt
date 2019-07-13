package com.zhudapps.darkcanary.main

import android.util.Log
import androidx.lifecycle.ViewModel

/**
 * Created by adrian mohnacs on 2019-07-12
 */
class MainViewModel: ViewModel() {

    companion object {
        const val TAG = "MainViewModel"
    }

    fun initUserLocation() {
        Log.e(TAG, "yay! our viewmodel is functioning properly!")
    }

    fun getWeeksForecast() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}