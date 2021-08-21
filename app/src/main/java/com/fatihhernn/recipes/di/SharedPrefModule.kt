package com.fatihhernn.recipes.di

import android.content.Context
import com.fatihhernn.recipes.data.database.sharedPref.SharedLocalDataSource
import com.fatihhernn.recipes.data.database.sharedPref.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class SharedPrefModule {

    @Singleton
    @Provides
    fun sharedPrefManager(@ApplicationContext context: Context): SharedPrefManager {
        return SharedPrefManager(context)
    }

    @Provides
    @Singleton
    fun localDataSource(sharedPrefManager :SharedPrefManager): SharedLocalDataSource {
        return SharedLocalDataSource(sharedPrefManager)
    }
}