package com.zhudapps.darkcanary.forecast

import com.zhudapps.darkcanary.domain.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Module
class ForecastFragmentModule {

    @Provides
    fun provideForecastViewModel(dataManager: DataManager): ForecastViewModel {
        return ForecastViewModel(dataManager)
    }
}