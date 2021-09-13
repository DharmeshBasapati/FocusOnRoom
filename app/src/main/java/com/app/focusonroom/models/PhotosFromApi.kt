package com.app.focusonroom.models

import com.google.gson.annotations.SerializedName

data class PhotosFromApi(
    @SerializedName("albumId") val albumId : Int,
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("url") val url : String,
    @SerializedName("thumbnailUrl") val thumbnailUrl : String
)
