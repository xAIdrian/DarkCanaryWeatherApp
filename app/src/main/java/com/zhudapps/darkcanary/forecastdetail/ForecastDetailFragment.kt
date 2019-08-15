package com.zhudapps.darkcanary.forecastdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zhudapps.darkcanary.R
import com.zhudapps.darkcanary.dagger.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.forecast_detail_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastDetailFragment : DaggerFragment() {

    companion object {
        fun newInstance() = ForecastDetailFragment()
    }

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: ForecastDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (::factory.isInitialized) {
            viewModel = ViewModelProviders.of(this, factory).get(ForecastDetailViewModel::class.java)
            viewModel.getCurrentTimeMachineForcast()

            viewModel.displayDate.observe(this, Observer { date_title.text = it })

            viewModel.forecastLiveData.observe(this, Observer { timeMachineForecast ->
                timeMachineForecast?.let {

                    GlobalScope.launch {
                        location_title.text = viewModel.location()
                    }

                    val forecasts = it.daily?.forecasts
                    if (forecasts?.isNotEmpty() == true) {

                        forecasts[0].apply {

                            summary_title.text = summary

                            pecip_probability_text.text = precipProbability
                            cloud_cover_text.text = cloudCover
                            precip_itnesity_text.text = precipIntensity

                            humidity_text.text = humidity
                            temperature_text.text = temperature
                            uv_index_text.text = uvIndex

                            wind_bearing_text.text = windBearing
                            wind_speed_text.text = windSpeed
                            pressure_text.text = pressure
                        }
                    }
                }
            })
        }
    }

}
