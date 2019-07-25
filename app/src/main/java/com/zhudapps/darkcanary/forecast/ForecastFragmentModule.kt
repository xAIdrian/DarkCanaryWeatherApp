package com.zhudapps.darkcanary.forecast

import android.net.ConnectivityManager
import com.zhudapps.darkcanary.domain.ForecastRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Module
class ForecastFragmentModule {

    @Provides
    @Singleton
    fun provideForecastViewModel(repo: ForecastRepository, connectivityManager: ConnectivityManager): ForecastViewModel {
        return ForecastViewModel(repo, connectivityManager)
    }
}
