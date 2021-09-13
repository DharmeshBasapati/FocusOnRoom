package com.app.focusonroom.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.focusonroom.room.entity.Photos

@Dao
interface PhotosDao {

    @Query("Select * from photos")
    fun getAllPhotos(): List<Photos>

    @Insert
    fun insertAllPhotos(photosList: List<Photos>)

}