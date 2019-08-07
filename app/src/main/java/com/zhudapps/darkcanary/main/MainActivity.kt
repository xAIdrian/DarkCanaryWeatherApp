package com.zhudapps.darkcanary.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.legacy.app.ActionBarDrawerToggle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.zhudapps.darkcanary.R
import com.zhudapps.darkcanary.dagger.viewmodel.ViewModelProviderFactory
import com.zhudapps.darkcanary.forecast.ForecastFragment
import com.zhudapps.darkcanary.forecast.OnFragmentListener
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Following the decision for one Activity here we contain the ViewPager2 used with ForecastFragments
 * and initiate calls for user location
 */
class MainActivity : FragmentActivity(), HasSupportFragmentInjector, OnFragmentListener,
    NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val TAG = "MainActivity"
        private const val MY_PERMISSIONS_REQUEST_COURSE_LOCATION = 101
    }

    @Inject
    lateinit var factory: ViewModelProviderFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var viewModel: MainViewModel? = null
    private var currentFragmentPos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        AndroidInjection.inject(this);

        if (::factory.isInitialized) {
            viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

            managePermissions()
        }
        setUpNavigationDrawer()
        setUpViewPager()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_COURSE_LOCATION -> viewModel?.initUserLocation()
        }
    }

    private fun setUpNavigationDrawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setActionBar(toolbar).apply {
            title = ""
            //setDisplayShowTitleEnabled(false);
        }

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.drawable.ic_menu_white,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    private fun setUpViewPager() {
        val pager = findViewById<ViewPager2>(R.id.view_pager)
        with(pager) {
            val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, this@MainActivity.lifecycle)
            adapter = viewPagerAdapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                    //todo further animations belong here.
                    //translate items left from center at positionOffsetPixels / 2
                }
            })
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

    override fun fragmentTrackingCallback(position: Int) {
        currentFragmentPos = position
    }

    override fun getCurrentFragmentIndex(): Int {
        return currentFragmentPos
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
        override fun getItemCount(): Int = 7

        override fun createFragment(position: Int): Fragment  {
            return ForecastFragment.newInstance(position)
        }
    }
}
