package com.zhudapps.darkcanary.dagger.activity

import com.zhudapps.darkcanary.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity
}