package com.zhudapps.darkcanary.model

/**
 * Created by adrian mohnacs on 2019-07-13
 */
//@Entity
data class Hourly (
    var summary: String,
    var data: ArrayList<Forecast>,
    var icon: String
)

