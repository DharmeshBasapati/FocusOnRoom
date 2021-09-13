package com.app.focusonroom.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.focusonroom.room.dao.PhotosDao
import com.app.focusonroom.viewmodels.AllUsersViewModel
import com.app.focusonroom.viewmodels.MainViewModel

class ViewModelFactory(private val photosDao: PhotosDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(photosDao) as T
        }

        if (modelClass.isAssignableFrom(AllUsersViewModel::class.java)) {
            return AllUsersViewModel(photosDao) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }
}