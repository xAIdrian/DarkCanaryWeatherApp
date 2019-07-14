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

        const val FORECAST_DAY_OFFSET = "forecast_day_offset"

        @JvmStatic
        fun newInstance(dayOffset: Int) =
            ForecastFragment().apply {
                arguments = Bundle().apply {
                    putInt(FORECAST_DAY_OFFSET, dayOffset)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            dayOffset = it.getInt(FORECAST_DAY_OFFSET)
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private var dayOffset = 0
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
            val mainViewModel = activity?.let { ViewModelProviders.of(it, factory).get(MainViewModel::class.java) }
            viewModel = ViewModelProviders.of(this, factory).get(ForecastViewModel::class.java)

            viewModel.mainViewModel = mainViewModel
            viewModel.dayOffset = dayOffset

            viewModel.getForecast()

            viewModel.forcastLiveData.observe(this, Observer {
                Log.e(TAG, it.toString())
            })
        }
    }
}
