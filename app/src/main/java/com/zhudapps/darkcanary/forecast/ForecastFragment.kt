package com.zhudapps.darkcanary.forecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.zhudapps.darkcanary.R
import com.zhudapps.darkcanary.main.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ForecastFragment : DaggerFragment() {

    companion object {
        private const val TAG = "ForecastFragment"
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.forecast_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (::factory.isInitialized) {
            val mainViewModel =  ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
            viewModel = ViewModelProviders.of(this, factory).get(ForecastViewModel::class.java)

            viewModel.mainViewModel = mainViewModel
            viewModel.getForecast()

            viewModel.forcastLiveData.observe(this, Observer {
                Log.e(TAG, it.toString())
            })
        }
    }
}
