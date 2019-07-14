package com.zhudapps.darkcanary.dagger

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.zhudapps.darkcanary.dagger.viewmodel.ViewModelProviderFactory
import com.zhudapps.darkcanary.domain.DataManager
import com.zhudapps.darkcanary.domain.IDataManager
import com.zhudapps.darkcanary.forecast.ForecastViewModel
import com.zhudapps.darkcanary.main.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Module
class ApplicationModule {

    @Provides
    @Singleton
    @Named("application.context")
    fun provideContext(app: Application): Context {
        return app
    }

    @Provides
    fun provideLocationService(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideViewModelProvider(
        mainViewModel: MainViewModel,
        forecast: ForecastViewModel
    ): ViewModelProvider.Factory {
        return ViewModelProviderFactory(mainViewModel, forecast)
    }

    @Provides
    fun provideDataManager(): IDataManager {
        return DataManager()
    }
}