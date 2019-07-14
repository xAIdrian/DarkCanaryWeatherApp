package com.zhudapps.darkcanary.forecast

import com.zhudapps.darkcanary.domain.DataManager
import com.zhudapps.darkcanary.domain.retrofit.DarkSkyEndpoint
import com.zhudapps.darkcanary.domain.retrofit.RetrofitServiceBuilder
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
    fun provideForecastViewModel(dataManager: DataManager): ForecastViewModel {
        return ForecastViewModel(dataManager)
    }
}