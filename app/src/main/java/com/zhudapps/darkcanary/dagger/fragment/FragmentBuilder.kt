package com.zhudapps.darkcanary.dagger.fragment

import com.zhudapps.darkcanary.forecast.ForecastFragment
import com.zhudapps.darkcanary.forecast.ForecastFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [ ForecastFragmentModule::class ])
    abstract fun provideMapFragment(): ForecastFragment
}