package com.zhudapps.darkcanary.dagger

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.zhudapps.darkcanary.dagger.viewmodel.ViewModelProviderFactory
import com.zhudapps.darkcanary.domain.retrofit.DarkSkyEndpoint
import com.zhudapps.darkcanary.domain.retrofit.RetrofitServiceBuilder
import com.zhudapps.darkcanary.forecast.ForecastViewModel
import com.zhudapps.darkcanary.forecastdetail.ForecastDetailViewModel
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
        forecastViewModel: ForecastViewModel,
        forecastDetailViewModel: ForecastDetailViewModel
    ): ViewModelProvider.Factory {
        return ViewModelProviderFactory(mainViewModel, forecastViewModel,forecastDetailViewModel)
    }

    @Provides
    @Singleton
    fun providesDarkSkyEndpoint(): DarkSkyEndpoint {
        return RetrofitServiceBuilder.createService(DarkSkyEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}