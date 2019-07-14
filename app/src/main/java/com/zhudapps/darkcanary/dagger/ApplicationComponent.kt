package com.zhudapps.darkcanary.dagger

import android.content.Context
import com.zhudapps.darkcanary.DarkCanaryApp
import com.zhudapps.darkcanary.dagger.activity.ActivityBuilder
import com.zhudapps.darkcanary.dagger.fragment.FragmentBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityBuilder::class,
    FragmentBuilder::class
])
interface ApplicationComponent : AndroidInjector<DarkCanaryApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}