package com.app.focusonroom.room.builder

import android.content.Context
import androidx.room.Room
import com.app.focusonroom.room.database.FocusDatabase

object DatabaseBuilder {

    private var dbInstance: FocusDatabase? = null

    fun getDBInstance(context: Context): FocusDatabase {
        if (dbInstance == null) {
            synchronized(FocusDatabase::class) {
                dbInstance = buildFocusDB(context)
            }
        }
        return dbInstance!!
    }

    private fun buildFocusDB(context: Context): FocusDatabase? {
        return Room.databaseBuilder(
            context.applicationContext,
            FocusDatabase::class.java,
            "FocusDatabase"
        ).build()
    }

}