package com.app.focusonroom.network

import com.app.focusonroom.models.PhotosFromApi
import com.app.focusonroom.models.UsersFromApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiServices {

    @GET("photos")//https://jsonplaceholder.typicode.com/photos?albumId=1
    fun getPhotos(@Query("albumId") albumId: Int): Call<List<PhotosFromApi>>

    @GET("users")//https://jsonplaceholder.typicode.com/users
    fun getAllUsers(): Call<List<UsersFromApi>>

}