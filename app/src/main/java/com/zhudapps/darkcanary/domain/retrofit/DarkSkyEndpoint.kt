package com.zhudapps.darkcanary.domain.retrofit

import com.zhudapps.darkcanary.model.TimeMachineForecast
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by adrian mohnacs on 2019-07-13
 */
interface DarkSkyEndpoint {

    @GET("/forecast/{key}/{latitude},{longitude},{time}")
    suspend fun getTimeMachineForecast(
        @Path("key") key: String,
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Path("time") time: Long
    ): Response<TimeMachineForecast>
}