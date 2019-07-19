package com.zhudapps.darkcanary.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.zhudapps.darkcanary.R
import com.zhudapps.darkcanary.main.MainViewModel
import com.zhudapps.darkcanary.model.WeatherIcon
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.forecast_fragment.*
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
            val mainViewModel = activity?.let { ViewModelProviders.of(it, factory).get(MainViewModel::class.java) }
            viewModel = ViewModelProviders.of(this, factory).get(ForecastViewModel::class.java)

            viewModel.mainViewModel = mainViewModel
            arguments?.getInt(FORECAST_DAY_OFFSET)?.let {
                //viewModel.forecastIndex = it
                viewModel.getForecast(it)
            }

            details_button.setOnClickListener {
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show()
            }

            viewModel.forecastLiveData.observe(this, Observer {

                if (!it.daily.forecasts.isNullOrEmpty()) {

                    progress_spinner.visibility = View.GONE
                    fragment_content.visibility = View.VISIBLE

                    val daily = it.daily.forecasts[0]

                    //date_title.text = viewModel.getDisplayDate(daily.time)

                    icon_image_view.setImageResource(
                        when (daily.icon) {
                            WeatherIcon.CLEAR_DAY -> WeatherIcon.CLEAR_DAY.value
                            WeatherIcon.CLEAR_NIGHT -> WeatherIcon.CLEAR_NIGHT.value
                            WeatherIcon.CLOUDY -> WeatherIcon.CLOUDY.value
                            WeatherIcon.FOG -> WeatherIcon.FOG.value
                            WeatherIcon.HAIL -> WeatherIcon.HAIL.value
                            WeatherIcon.PARTLY_CLOUDY_DAY -> WeatherIcon.PARTLY_CLOUDY_DAY.value
                            WeatherIcon.PARTLY_CLOUDY_NIGHT -> WeatherIcon.PARTLY_CLOUDY_NIGHT.value
                            WeatherIcon.RAIN -> WeatherIcon.RAIN.value
                            WeatherIcon.SLEET -> WeatherIcon.SLEET.value
                            WeatherIcon.SNOW -> WeatherIcon.SNOW.value
                            WeatherIcon.THUNDERSTORM -> WeatherIcon.THUNDERSTORM.value
                            WeatherIcon.TORNADO -> WeatherIcon.TORNADO.value
                            WeatherIcon.WIND -> WeatherIcon.WIND.value
                        }
                    )
                    summary_title.text = daily.summary
                    high_temp.text = daily.apparentTemperatureHigh
                    low_temp.text = daily.apparentTemperatureLow

                }
            })
        }
    }
}
