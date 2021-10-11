package com.yologger.simpleweather.di

import android.content.Context
import com.yologger.simpleweather.data.util.LocationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Provides
    @Singleton
    fun provideLocationUtil(@ApplicationContext context: Context): LocationUtil {
        return LocationUtil(context)
    }
}