package com.app.focusonroom.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusonroom.models.UsersFromApi
import com.app.focusonroom.network.RetrofitBuilder
import com.app.focusonroom.room.dao.PhotosDao
import com.app.focusonroom.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllUsersViewModel(private val photosDao: PhotosDao) : ViewModel() {

    private val users = MutableLiveData<Resource<List<UsersFromApi>>>()

    init {

        fetchUsers()

    }

    private fun fetchUsers() {

        users.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {

            try {

                Log.i(TAG, "fetchUsers: FROM API")
                RetrofitBuilder.myApiServices.getAllUsers()
                    .enqueue(object : Callback<List<UsersFromApi>> {
                        override fun onResponse(
                            call: Call<List<UsersFromApi>>,
                            response: Response<List<UsersFromApi>>
                        ) {
                            Log.d(TAG, "onResponse: $response")

                            val usersListFromApi = response.body()
                            users.postValue(Resource.success(usersListFromApi))

                        }

                        override fun onFailure(call: Call<List<UsersFromApi>>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")
                            users.postValue(Resource.error(null, t.message))
                        }

                    })

                
            } catch (e: Exception) {
                Log.e(TAG, "fetchUsers: ${e.message.toString()}")
                users.postValue(Resource.error(null, e.message.toString()))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<UsersFromApi>>> {
        return users
    }

    companion object {
        private const val TAG = "###AllUsersViewModel"
    }

}