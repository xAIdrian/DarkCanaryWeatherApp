package com.zhudapps.darkcanary.forecast

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        const val FORECAST_DAY_OFFSET_INDEX = "forecast_day_offset"

        @JvmStatic
        fun newInstance(dayOffset: Int) =
            ForecastFragment().apply {
                arguments = Bundle().apply {
                    putInt(FORECAST_DAY_OFFSET_INDEX, dayOffset)
                }
            }
    }

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: ForecastViewModel
    private var listener: OnFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (::factory.isInitialized && activity != null) {

            val mainViewModel = activity?.let { ViewModelProviders.of(activity!!, factory).get(MainViewModel::class.java) }
            viewModel = ViewModelProviders.of(activity!!, factory)
                .get(FORECAST_VIEWMODEL_KEY, ForecastViewModel::class.java)

            viewModel.mainViewModel = mainViewModel

            arguments?.getInt(FORECAST_DAY_OFFSET_INDEX)?.let {
                listener?.fragmentTrackingCallback(it)
                viewModel.readyForNextCall = true

                //todo check if it's the current fragment, that we're tracking in Activity
                // if it is not the current fragment and we are +1 or -1 make the change to the OFFSET_INDEX
                // and make the request still...
                // when we are making the call when we are swiping, then we need to check a similar condition to keep
                // from making the call again...isVisibleFragment


                viewModel.getForecast(it)
            }

            details_button.setOnClickListener {
                viewModel.launchDetailsFragment()
                launchDetailsFragment()
            }

            viewModel.forecastLiveData.observe(this, Observer {

                if (!it.daily.forecasts.isNullOrEmpty()) {
                    setForcastData(it)
                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun launchDetailsFragment() {
        if (listener?.getCurrentFragmentIndex() != arguments?.getInt(FORECAST_DAY_OFFSET_INDEX)) { return }

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.content_main, ForecastDetailFragment.newInstance())
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun setForcastData(timeMachineForecast: TimeMachineForecast) {

        if (!isVisibleFragment()) { return }

        Log.e(TAG, "${arguments?.getInt(FORECAST_DAY_OFFSET_INDEX)} ::: forecast datareturned")
        viewModel.readyForNextCall = false

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

    fun isVisibleFragment(): Boolean {
        return listener?.getCurrentFragmentIndex() == arguments?.getInt(FORECAST_DAY_OFFSET_INDEX)
    }
}
