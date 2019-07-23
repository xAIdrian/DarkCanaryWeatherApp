package com.zhudapps.darkcanary.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zhudapps.darkcanary.R
import com.zhudapps.darkcanary.dagger.viewmodel.ViewModelProviderFactory
import com.zhudapps.darkcanary.dagger.viewmodel.ViewModelProviderFactory.Companion.FORECAST_VIEWMODEL_KEY
import com.zhudapps.darkcanary.forecastdetail.ForecastDetailFragment
import com.zhudapps.darkcanary.main.MainViewModel
import com.zhudapps.darkcanary.model.TimeMachineForecast
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
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (::factory.isInitialized) {
            val mainViewModel = activity?.let { ViewModelProviders.of(it, factory).get(MainViewModel::class.java) }
            viewModel = ViewModelProviders.of(this@ForecastFragment, factory)
                .get(FORECAST_VIEWMODEL_KEY, ForecastViewModel::class.java)

            viewModel.mainViewModel = mainViewModel
            arguments?.getInt(FORECAST_DAY_OFFSET)?.let {
                viewModel.getForecast(it)
            }

            details_button.setOnClickListener {
                viewModel.launchDetailsFragment()
            }

            viewModel.forecastLiveData.observe(this, Observer {

                if (!it.daily.forecasts.isNullOrEmpty()) {
                    setForcastData(it)
                }
            })

            viewModel.launchDetailsFragmentEvent.observe(this, Observer {
                launchDetailsFragment()
            })
        }
    }

    private fun launchDetailsFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.content, ForecastDetailFragment.newInstance())
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun setForcastData(timeMachineForecast: TimeMachineForecast) {

        progress_spinner.visibility = View.GONE
        fragment_content.visibility = View.VISIBLE

        val daily = timeMachineForecast.daily.forecasts[0]

        if (timeMachineForecast.dayOfWeek.isNotEmpty()) {
            date_title.text = timeMachineForecast.dayOfWeek
        }

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
                else -> WeatherIcon.UNKNOWN.value
            }
        )
        summary_title.text = daily.summary
        high_temp.text = daily.apparentTemperatureHigh
        low_temp.text = daily.apparentTemperatureLow

    }
}
