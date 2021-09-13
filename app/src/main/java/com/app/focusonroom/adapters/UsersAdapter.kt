package com.app.focusonroom.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusonroom.R
import com.app.focusonroom.databinding.ListItemPhotosBinding
import com.app.focusonroom.databinding.ListItemUsersBinding
import com.app.focusonroom.models.UsersFromApi
import com.app.focusonroom.room.entity.Photos
import com.bumptech.glide.Glide

class UsersAdapter(var usersFromApiList: List<UsersFromApi>) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    class UsersViewHolder(private val listItemUsersBinding: ListItemUsersBinding) :
        RecyclerView.ViewHolder(listItemUsersBinding.root) {
        fun bind(user: UsersFromApi) {
            /*Glide.with(listItemUsersBinding.ivUserPic.context).load("https://www.seekpng.com/png/detail/245-2454602_tanni-chand-default-user-image-png.png")
                .placeholder(R.drawable.ic_baseline_supervisor_account_24).into(listItemUsersBinding.ivUserPic)*/
            listItemUsersBinding.tvUsername.text = user.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UsersViewHolder(
            ListItemUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(usersFromApiList[position])
    }

    override fun getItemCount() = usersFromApiList.size

    fun addUsers(usersFromApiListFromAPI: List<UsersFromApi>) {
        usersFromApiList = usersFromApiListFromAPI
    }
}