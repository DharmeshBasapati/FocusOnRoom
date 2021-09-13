package com.app.focusonroom.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photos(
    @PrimaryKey val photoId: Int,
    @ColumnInfo(name = "albumId") val albumId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String,
)