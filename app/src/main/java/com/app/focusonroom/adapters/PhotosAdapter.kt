package com.app.focusonroom.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusonroom.R
import com.app.focusonroom.databinding.ListItemPhotosBinding
import com.app.focusonroom.room.entity.Photos
import com.bumptech.glide.Glide

class PhotosAdapter(var photosFromApiList: List<Photos>) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    class PhotosViewHolder(private val listItemBinding: ListItemPhotosBinding) :
        RecyclerView.ViewHolder(listItemBinding.root) {
        fun bind(photo: Photos) {
            Glide.with(listItemBinding.ivPhoto.context).load("https://images.unsplash.com/photo-1510561467401-91b9835f745e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80")
                .placeholder(R.drawable.ic_baseline_image_24).into(listItemBinding.ivPhoto)
            listItemBinding.tvTitle.text = photo.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotosViewHolder(
            ListItemPhotosBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photosFromApiList[position])
    }

    override fun getItemCount() = photosFromApiList.size

    fun addPhotos(photosFromApiListFromAPI: List<Photos>) {
        photosFromApiList = photosFromApiListFromAPI
    }
}