package com.zhudapps.darkcanary.dagger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhudapps.darkcanary.forecast.ForecastViewModel
import com.zhudapps.darkcanary.main.MainViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Singleton
class ViewModelProviderFactory @Inject
constructor(
    private val mainViewModel: MainViewModel,
    private val forecastViewModel: ForecastViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> mainViewModel as T
            modelClass.isAssignableFrom(ForecastViewModel::class.java) -> forecastViewModel as T
            else -> throw IllegalArgumentException("ViewModelProviderFactory Unknown ViewModel class: " + modelClass.name)
        }
    }
}