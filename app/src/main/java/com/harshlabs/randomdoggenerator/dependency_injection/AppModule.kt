package com.harshlabs.randomdoggenerator.dependency_injection

import android.content.Context
import com.google.gson.GsonBuilder
import com.harshlabs.randomdoggenerator.data.local.InternalStorageManager
import com.harshlabs.randomdoggenerator.data.remote.BASE_URL
import com.harshlabs.randomdoggenerator.data.remote.DogGeneratorApi
import com.harshlabs.randomdoggenerator.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideDogGeneratorApi(): DogGeneratorApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DogGeneratorApi::class.java)
    }

    @Singleton
    @Provides
    fun provideInternalStorageManager(application: BaseApplication): InternalStorageManager {
        return InternalStorageManager(application)
    }
}