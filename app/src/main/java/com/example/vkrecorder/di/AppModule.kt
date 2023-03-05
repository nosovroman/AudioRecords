package com.example.vkrecorder.di

import android.app.Application
import com.example.vkrecorder.data.remote.ApiServiceVk
import com.example.vkrecorder.data.repository.VkRepositoryImpl
import com.example.vkrecorder.domain.repository.VkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = "https://api.vk.com/method/"

    @Provides
    @Singleton
    fun provideRecorderDir(context: Application): File {
        return File(context.cacheDir.absolutePath)
    }

    @Provides
    @Singleton
    fun provideVkApi(baseUrl: String): ApiServiceVk {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiServiceVk::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        apiServiceVk: ApiServiceVk
    ): VkRepository {
        return VkRepositoryImpl(apiServiceVk)
    }
}