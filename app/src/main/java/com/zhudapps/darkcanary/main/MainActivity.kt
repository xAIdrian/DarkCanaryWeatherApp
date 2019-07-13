package com.zhudapps.darkcanary.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.zhudapps.darkcanary.R
import com.zhudapps.darkcanary.dagger.viewmodel.ViewModelProviderFactory
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
        setContentView(R.layout.activity_main)

        if (::factory.isInitialized) {
            viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

            //viewModel?.initUserLocation()
        }
    }

    override fun onResume() {
        super.onResume()

        managePermissions()
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
                Log.e(TAG, "show explanation")
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
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

                // MY_PERMISSIONS_REQUEST_COURSE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                Log.e(TAG, "No explanation needed, we can request the permission")
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
}
