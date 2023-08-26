package com.example.ive.di

import android.content.Context
import androidx.room.Room
import com.example.ive.dao.PhotoDao
import com.example.ive.data.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context, AppDataBase::class.java, "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(dataBase: AppDataBase): PhotoDao{
        return dataBase.photoDao()
    }
}