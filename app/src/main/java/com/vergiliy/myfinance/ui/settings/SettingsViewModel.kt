package com.vergiliy.myfinance.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vergiliy.myfinance.data.repositories.SettingsRepositoryImpl
import com.vergiliy.myfinance.domain.models.Settings
import com.vergiliy.myfinance.domain.usecase.settings.GetSettings
import com.vergiliy.myfinance.domain.usecase.settings.SaveSettings
import com.vergiliy.myfinance.ui.base.BaseViewModal
import com.vergiliy.myfinance.utils.SettingsState
import kotlinx.coroutines.launch


class SettingsViewModel: BaseViewModal() {
    private val saveSettingsUseCase = SaveSettings(SettingsRepositoryImpl())
    private val getSettingsUseCase = GetSettings(SettingsRepositoryImpl())

    private val _settings = MutableLiveData<Settings>().apply {
        value = Settings()
    }

    init {
        viewModelScope.launch {
            _settings.value = getSettingsUseCase.getAll()
        }
    }

    val settings: LiveData<Settings> = _settings

    fun setSettings(data: Settings){
        viewModelScope.launch {
            saveSettingsUseCase.execute(data)
            SettingsState.enabledComments = data.enabledComments ?: false
            SettingsState.hourlyPayment = data.hourlyPayment ?: false
            SettingsState.isReplayPayments = data.isReplayPayments ?: false
            SettingsState.paymentReceived = data.paymentReceived ?: false
            _settings.value = data
        }
    }
}