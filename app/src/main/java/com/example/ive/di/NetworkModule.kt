package com.example.ive.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.ive.BuildConfig
import com.example.ive.api.PhotoApi
import com.example.ive.dao.PhotoDao
import com.example.ive.data.AppDataBase
import com.example.ive.network.BaseInterceptor
import com.example.ive.utils.NetworkChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkChecker(context: Application): NetworkChecker {
        return NetworkChecker(context)
    }

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase{
        return Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(dataBase: AppDataBase): PhotoDao {
        return dataBase.photoDao()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideOkhttp():OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(BaseInterceptor())
        .authenticator(BaseInterceptor())
        .build()

    @Provides
    @Singleton
    fun providePhotoApi(retrofit: Retrofit): PhotoApi{
        return retrofit.create(PhotoApi::class.java)
    }

}