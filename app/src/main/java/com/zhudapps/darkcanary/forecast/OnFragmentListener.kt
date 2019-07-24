package com.zhudapps.darkcanary.forecast

/**
 * Created by adrian mohnacs on 2019-07-23
 */
interface OnFragmentListener {
    // TODO: Update argument type and name
    fun fragmentTrackingCallback(position: Int)
    fun getCurrentFragmentIndex(): Int
}