package com.app.focusonroom.abstraction

import androidx.appcompat.app.AppCompatActivity

abstract class MyAbstractActivity : AppCompatActivity() {

    abstract fun prepareList()

    abstract fun initViewModel()

    abstract fun observeData()

}