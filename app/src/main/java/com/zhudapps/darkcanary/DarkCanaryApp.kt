package com.zhudapps.darkcanary

import android.app.Activity
import com.zhudapps.darkcanary.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class DarkCanaryApp : DaggerApplication(), HasActivityInjector {

    @Inject //using dispatching to support our appcompat activities
    lateinit var activityAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return activityAndroidInjector
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerApplicationComponent.factory().create(this)
    }
}
