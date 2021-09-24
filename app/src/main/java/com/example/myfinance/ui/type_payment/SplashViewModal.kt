package com.example.myfinance.ui.type_payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.repositories.SettingsRepositoryImpl
import com.example.myfinance.domain.models.Settings
import com.example.myfinance.domain.usecase.settings.GetSettings
import com.example.myfinance.ui.base.BaseViewModal
import kotlinx.coroutines.launch

class SplashViewModal: BaseViewModal() {
    private val getSettingsUseCase = GetSettings(SettingsRepositoryImpl())

    private val _settings = MutableLiveData<Settings>().apply {
        value = null
    }

    val settings: LiveData<Settings> = _settings

    init {
        viewModelScope.launch {
            _settings.value = getSettingsUseCase.getAll()
        }
    }
}