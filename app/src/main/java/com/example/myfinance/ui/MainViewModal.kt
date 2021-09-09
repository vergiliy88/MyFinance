package com.example.myfinance.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModal : ViewModel() {

    private val _positionMenu = MutableLiveData<Int?>().apply {
        value = null
    }

    fun setCurrentMenuId(id: Int) {
        _positionMenu.value = id
    }

    val positionMenu: LiveData<Int?> = _positionMenu
}