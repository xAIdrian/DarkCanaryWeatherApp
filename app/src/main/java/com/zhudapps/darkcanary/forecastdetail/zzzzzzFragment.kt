package com.zhudapps.darkcanary.forecastdetail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zhudapps.darkcanary.R
import com.zhudapps.darkcanary.dagger.viewmodel.ViewModelProviderFactory

import com.zhudapps.darkcanary.forecastdetail.dummy.DummyContent
import com.zhudapps.darkcanary.model.Forecast
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.forecast_detail_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [zzzzzzFragment.OnListFragmentInteractionListener] interface.
 */
class zzzzzzFragment : DaggerFragment() {

    companion object {
        fun newInstance() = ForecastDetailFragment()
    }

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: ForecastDetailViewModel

    private val hourlyForecastList = ArrayList<Forecast>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_zzzzzz_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = ForecastDetailAdapter(hourlyForecastList)
            }
        }
        return view
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

                        //update adapter
                    }
                }
            })
        }
    }
}
