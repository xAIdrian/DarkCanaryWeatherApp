package com.zhudapps.darkcanary.dagger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhudapps.darkcanary.forecast.ForecastViewModel
import com.zhudapps.darkcanary.forecastdetail.ForecastDetailViewModel
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
    private val forecastViewModel: ForecastViewModel,
    private val forecastDetailViewModel: ForecastDetailViewModel
) : ViewModelProvider.Factory {

    companion object {
        const val FORECAST_VIEWMODEL_KEY = "forecast_viewmodel_key"
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {
            // use isAssignable to determine if the modelClass is the same type as T,
            // "when" it is then we just grab the one that was instantiated by our dagger graph
            modelClass.isAssignableFrom(MainViewModel::class.java) -> mainViewModel as T
            //todo we may need to remove this if we go with our Base(Connectivity)Fragment approach
            modelClass.isAssignableFrom(ForecastViewModel::class.java) -> forecastViewModel as T
            modelClass.isAssignableFrom(ForecastDetailViewModel::class.java) -> forecastDetailViewModel as T
            else -> throw IllegalArgumentException("ViewModelProviderFactory Unknown ViewModel class: " + modelClass.name)
        }
    }
}