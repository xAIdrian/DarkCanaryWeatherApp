package com.zhudapps.darkcanary.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.zhudapps.darkcanary.R
import com.zhudapps.darkcanary.forecast.ForecastFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Following the decision for one Activity here we contain the ViewPager2 used with ForecastFragments
 * and initiate calls for user location
 */
class MainActivity : DaggerAppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val MY_PERMISSIONS_REQUEST_COURSE_LOCATION = 101
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (::factory.isInitialized) {
            viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

            managePermissions()
        }
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val pager = findViewById<ViewPager2>(R.id.view_pager)
        with(pager) {
            val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, this@MainActivity.lifecycle)
            adapter = viewPagerAdapter
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_COURSE_LOCATION -> viewModel?.initUserLocation()
        }
    }

    private fun managePermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                //permissions previously denied
                findViewById<View>(R.id.content).apply {
                    Snackbar.make(
                        this,
                        R.string.please_permission,
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(R.string.grant, ShowPermissionRequest()).show()
                }

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST_COURSE_LOCATION
                )
            }
        } else {
            //permission granted
            Log.e(TAG, "permission")
            viewModel?.initUserLocation()
        }
    }

    inner class ShowPermissionRequest : View.OnClickListener {

        override fun onClick(v: View) {
            // Code to undo the user's last action
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSIONS_REQUEST_COURSE_LOCATION
            )
        }
    }

    private inner class ViewPagerAdapter(fm: FragmentManager, lifeCycleFromActivity: Lifecycle) : FragmentStateAdapter(fm, lifeCycleFromActivity) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment  = ForecastFragment()
    }
}
