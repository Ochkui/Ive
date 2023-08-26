package com.example.ive.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ive.dao.PhotoDao
import com.example.ive.network.model.PhotoEntity

@Database(
    entities = [PhotoEntity::class],
    version = 1,
    exportSchema = false
    )
@TypeConverters(
    UrlsTypeConverter::class,
    UserTypeConverter::class
)
abstract class AppDataBase: RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}