package com.zhudapps.darkcanary.forecastdetail

import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import com.zhudapps.darkcanary.domain.ForecastRepository
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class ForecastDetailFragmentModule {

    @Provides
    @Singleton
    fun provideForecastViewModel(repo: ForecastRepository, connectivityManager: ConnectivityManager, geocoder: Geocoder): ForecastDetailViewModel {
        return ForecastDetailViewModel(repo, connectivityManager, geocoder)
    }
}
