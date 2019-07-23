package com.zhudapps.darkcanary.forecastdetail

import android.net.ConnectivityManager
import com.zhudapps.darkcanary.domain.ForecastRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ForecastDetailFragmentModule {

    @Provides
    @Singleton
    fun provideForecastViewModel(repo: ForecastRepository, connectivityManager: ConnectivityManager): ForecastDetailViewModel {
        return ForecastDetailViewModel(repo, connectivityManager)
    }
}
