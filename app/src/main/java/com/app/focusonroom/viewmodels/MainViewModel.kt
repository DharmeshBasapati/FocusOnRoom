package com.app.focusonroom.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusonroom.models.PhotosFromApi
import com.app.focusonroom.network.RetrofitBuilder
import com.app.focusonroom.room.dao.PhotosDao
import com.app.focusonroom.room.entity.Photos
import com.app.focusonroom.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val photosDao: PhotosDao) : ViewModel() {

    private val photos = MutableLiveData<Resource<List<Photos>>>()

    init {

        fetchPhotos()

    }

    private fun fetchPhotos() {

        photos.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val photosListFromDB = photosDao.getAllPhotos()

                if (photosListFromDB.isEmpty()) {
                    Log.i(TAG, "fetchPhotos: FROM API")
                    RetrofitBuilder.myApiServices.getPhotos(1)
                        .enqueue(object : Callback<List<PhotosFromApi>> {
                            override fun onResponse(
                                call: Call<List<PhotosFromApi>>,
                                response: Response<List<PhotosFromApi>>
                            ) {
                                Log.d(TAG, "onResponse: $response")

                                val photosListFromApi = response.body()
                                val photosToInsertInDB = mutableListOf<Photos>()

                                photosListFromApi?.forEach { photos ->

                                    val photo = Photos(
                                        photoId = photos.id,
                                        albumId = photos.albumId,
                                        title = photos.title,
                                        url = photos.url,
                                        thumbnailUrl = photos.thumbnailUrl
                                    )

                                    photosToInsertInDB.add(photo)

                                }

                                viewModelScope.launch(Dispatchers.IO){
                                    photosDao.insertAllPhotos(photosToInsertInDB)
                                }.invokeOnCompletion {
                                    photos.postValue(Resource.success(photosToInsertInDB))
                                }

                            }

                            override fun onFailure(call: Call<List<PhotosFromApi>>, t: Throwable) {
                                Log.e(TAG, "onFailure: ${t.message}")
                                photos.postValue(Resource.error(null, t.message))
                            }

                        })

                } else {
                    Log.i(TAG, "fetchPhotos: FROM DB")
                    photos.postValue(Resource.success(photosListFromDB))
                }

            } catch (e: Exception) {
                Log.e(TAG, "fetchPhotos: ${e.message.toString()}")
                photos.postValue(Resource.error(null, e.message.toString()))
            }
        }
    }

    fun getPhotos(): LiveData<Resource<List<Photos>>> {
        return photos
    }

    companion object {
        private const val TAG = "###MainViewModel"
    }

}