package com.app.focusonroom.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.focusonroom.R
import com.app.focusonroom.abstraction.MyAbstractActivity
import com.app.focusonroom.adapters.UsersAdapter
import com.app.focusonroom.databinding.ActivityAllUsersBinding
import com.app.focusonroom.models.UsersFromApi
import com.app.focusonroom.room.builder.DatabaseBuilder
import com.app.focusonroom.utils.Status
import com.app.focusonroom.utils.ViewModelFactory
import com.app.focusonroom.viewmodels.AllUsersViewModel

class AllUsersActivity : MyAbstractActivity() {

    private lateinit var usersAdapter: UsersAdapter
    private lateinit var binding: ActivityAllUsersBinding
    private lateinit var allUsersViewModel: AllUsersViewModel

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "All Users"

        supportActionBar?.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_new_24,null))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prepareList()

        initViewModel()

        observeData()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun populateUsersList(usersList: List<UsersFromApi>) {
        usersAdapter.addUsers(usersList)
        usersAdapter.notifyItemInserted(usersList.size)
    }

    override fun prepareList() {
        binding.recyclerViewUsers.layoutManager = GridLayoutManager(this, 2)

        usersAdapter = UsersAdapter(arrayListOf())

        binding.recyclerViewUsers.adapter = usersAdapter
    }

    override fun initViewModel() {
        allUsersViewModel = ViewModelProvider(
            this,
            ViewModelFactory(DatabaseBuilder.getDBInstance(applicationContext).photosDao())
        ).get(AllUsersViewModel::class.java)
    }

    override fun observeData() {
        allUsersViewModel.getUsers().observe(this, { apiResponse ->
            when (apiResponse.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "IN SUCCESS: ${apiResponse.data}")
                    apiResponse.data?.let { usersList ->
                        populateUsersList(usersList)
                    }
                    binding.cpiProgress.visibility = View.GONE
                    binding.recyclerViewUsers.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Log.e(TAG, "IN ERROR: ${apiResponse.message}")
                    binding.cpiProgress.visibility = View.GONE
                    binding.recyclerViewUsers.visibility = View.GONE
                }
                Status.LOADING -> {
                    Log.i(TAG, "Loading data from API or Database")
                    binding.cpiProgress.visibility = View.VISIBLE
                    binding.recyclerViewUsers.visibility = View.GONE
                }
            }
        })
    }


    companion object {
        private const val TAG = "###AllUsersActivity"
    }
}