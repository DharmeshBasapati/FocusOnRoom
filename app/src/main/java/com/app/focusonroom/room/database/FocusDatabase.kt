package com.app.focusonroom.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.focusonroom.room.dao.PhotosDao
import com.app.focusonroom.room.entity.Photos

@Database(entities = [Photos::class],version = 1)
abstract class FocusDatabase : RoomDatabase() {

    abstract fun photosDao() : PhotosDao
}