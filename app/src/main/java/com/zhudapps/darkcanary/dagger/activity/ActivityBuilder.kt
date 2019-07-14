package com.zhudapps.darkcanary.dagger.activity

import com.zhudapps.darkcanary.dagger.fragment.FragmentBuilder
import com.zhudapps.darkcanary.main.MainActivity
import com.zhudapps.darkcanary.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [
        MainActivityModule::class,
        FragmentBuilder::class
    ])
    abstract fun bindMainActivity(): MainActivity
}