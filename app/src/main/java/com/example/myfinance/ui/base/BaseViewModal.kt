package com.example.myfinance.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModal<D> : ViewModel() {

    private val _data = MutableLiveData<D>().apply {
        value = null
    }

    val data: LiveData<D> = _data

    fun setData(data: D) {
        _data.value = data
    }
}