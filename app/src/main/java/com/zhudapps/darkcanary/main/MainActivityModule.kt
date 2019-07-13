package com.zhudapps.darkcanary.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.zhudapps.darkcanary.dagger.viewmodel.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Module
class MainActivityModule {

    @Provides
    fun provideMainViewModel(fusedLocationProviderClient: FusedLocationProviderClient): MainViewModel {
        return MainViewModel(fusedLocationProviderClient)
    }

    @Provides
    fun provideViewModelProvider(viewModel: MainViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}