package com.rizal.nontonmovieyuk.data.repository

import com.rizal.nontonmovieyuk.data.api.ApiService
import com.rizal.nontonmovieyuk.utils.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepositoryImpl(
        appPreferences: AppPreferences,
        retrofit: Retrofit
    ): Repository {
        return RepositoryImpl(
            appPreferences,
            retrofit.create(ApiService::class.java)
        )
    }
}