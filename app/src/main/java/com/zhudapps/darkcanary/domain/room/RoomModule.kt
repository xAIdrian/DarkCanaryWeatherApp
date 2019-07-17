package com.zhudapps.darkcanary.domain.room

import android.content.Context
import androidx.room.Room
import com.zhudapps.darkcanary.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by adrian mohnacs on 2019-07-14
 */
@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideForecastDao(context: Context): ForecastDao {
        return Room.databaseBuilder(
            context,
            ForecastDatabase::class.java,
            context.getString(R.string.forecast_database)
            ).build().forecastDao()
    }
}