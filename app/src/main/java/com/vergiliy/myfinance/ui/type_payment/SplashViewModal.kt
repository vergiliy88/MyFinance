package com.vergiliy.myfinance.ui.type_payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vergiliy.myfinance.data.repositories.SettingsRepositoryImpl
import com.vergiliy.myfinance.domain.models.Settings
import com.vergiliy.myfinance.domain.usecase.settings.GetSettings
import com.vergiliy.myfinance.ui.base.BaseViewModal
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