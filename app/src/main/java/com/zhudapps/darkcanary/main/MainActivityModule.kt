package com.zhudapps.darkcanary.main

import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Module
class MainActivityModule {

    @Provides
    @Singleton
    fun provideMainViewModel(fusedLocationProviderClient: FusedLocationProviderClient): MainViewModel {
        return MainViewModel(fusedLocationProviderClient)
    }
}