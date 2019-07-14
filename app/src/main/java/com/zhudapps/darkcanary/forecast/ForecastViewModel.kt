package com.zhudapps.darkcanary.forecast

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhudapps.darkcanary.domain.DataManager
import com.zhudapps.darkcanary.main.MainViewModel
import com.zhudapps.darkcanary.model.TimeMachineForecast
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val dataManager: DataManager) : ViewModel() {

    companion object {
        private const val TAG = "ForecastViewModel"
    }

    internal var mainViewModel: MainViewModel? = null

    init {
        Log.e(TAG, "fragment initialized")
    }
}
