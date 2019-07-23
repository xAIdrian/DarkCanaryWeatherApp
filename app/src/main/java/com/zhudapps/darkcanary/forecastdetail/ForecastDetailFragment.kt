package com.zhudapps.darkcanary.forecastdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zhudapps.darkcanary.R
import com.zhudapps.darkcanary.dagger.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
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
        }
    }

}
