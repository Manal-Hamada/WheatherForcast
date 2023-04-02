package com.example.wheatherforcast.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.wheatherforcast.home.model.Repository

open class BaseViewModel(var _repo: Repository?, var context: Context) :ViewModel() {

}