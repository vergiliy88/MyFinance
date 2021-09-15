package com.example.myfinance.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.repositories.SettingsRepositoryImpl
import com.example.myfinance.domain.models.Settings
import com.example.myfinance.domain.usecase.settings.GetSettings
import com.example.myfinance.domain.usecase.settings.SaveSettings
import com.example.myfinance.ui.base.BaseViewModal
import kotlinx.coroutines.launch


class SettingsViewModel: BaseViewModal() {
    private val saveSettingsUseCase = SaveSettings(SettingsRepositoryImpl())
    private val getSettingsUseCase = GetSettings(SettingsRepositoryImpl())

    private val _settings = MutableLiveData<Settings>().apply {
        value = Settings()
    }

    init {
        viewModelScope.launch {
            _settings.value = getSettingsUseCase.execute()
        }
    }

    val settings: LiveData<Settings> = _settings

    fun setSettings(data: Settings){
        viewModelScope.launch {
            saveSettingsUseCase.execute(data)
            _settings.value = data
        }
    }
}