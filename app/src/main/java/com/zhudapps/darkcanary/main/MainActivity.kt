package com.zhudapps.darkcanary.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zhudapps.darkcanary.R
import com.zhudapps.darkcanary.dagger.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Following the decision for one Activity here we contain the ViewPager2 used with ForecastFragments
 * and initiate calls for user location
 */
class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (::factory.isInitialized) {
            viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

            viewModel.initUserLocation()
        }
    }
}
