package com.app.focusonroom.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.focusonroom.abstraction.MyAbstractActivity
import com.app.focusonroom.adapters.PhotosAdapter
import com.app.focusonroom.databinding.ActivityMainBinding
import com.app.focusonroom.room.builder.DatabaseBuilder
import com.app.focusonroom.room.entity.Photos
import com.app.focusonroom.utils.Status
import com.app.focusonroom.utils.ViewModelFactory
import com.app.focusonroom.viewmodels.MainViewModel

class MainActivity : MyAbstractActivity() {

    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        prepareList()

        initViewModel()

        observeData()

        openAllUsers()

    }


    override fun prepareList() {
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)

        photosAdapter = PhotosAdapter(arrayListOf())

        binding.recyclerViewItems.adapter = photosAdapter

    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(DatabaseBuilder.getDBInstance(applicationContext).photosDao())
        ).get(MainViewModel::class.java)
    }

    override fun observeData() {
        mainViewModel.getPhotos().observe(this, { apiResponse ->
            when (apiResponse.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "IN SUCCESS: ${apiResponse.data}")
                    apiResponse.data?.let { photosList ->
                        populatePhotosList(photosList)
                    }
                    binding.cpiProgress.visibility = View.GONE
                    binding.recyclerViewItems.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Log.e(TAG, "IN ERROR: ${apiResponse.message}")
                    binding.cpiProgress.visibility = View.GONE
                    binding.recyclerViewItems.visibility = View.GONE
                }
                Status.LOADING -> {
                    Log.i(TAG, "Loading data from API or Database")
                    binding.cpiProgress.visibility = View.VISIBLE
                    binding.recyclerViewItems.visibility = View.GONE
                }
            }
        })
    }

    private fun openAllUsers() {
        binding.exfabAllUsers.setOnClickListener {
            startActivity(Intent(this,AllUsersActivity::class.java))
        }
    }

    private fun populatePhotosList(photosList: List<Photos>) {
        photosAdapter.addPhotos(photosList)
        photosAdapter.notifyItemInserted(photosList.size)
    }

    companion object {
        private const val TAG = "###MainActivity"
    }

}