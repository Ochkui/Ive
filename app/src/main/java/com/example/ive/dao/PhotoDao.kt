package com.example.ive.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ive.network.model.PhotoEntity

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: List<PhotoEntity>)

    @Query("SELECT * FROM photos where orderBy = :orderBy")
    suspend fun getAllPhotos(orderBy: String): List<PhotoEntity>

    @Query("DELETE FROM photos")
    suspend fun clearPhotos()
}