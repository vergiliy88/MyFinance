package com.vergiliy.myfinance.domain.usecase.settings

import com.vergiliy.myfinance.domain.models.Settings
import com.vergiliy.myfinance.domain.repositories.SettingsRepository

class GetSettings(private val settingsRepository: SettingsRepository) {
    suspend fun getAll(): Settings {
        return settingsRepository.getSettings()
    }
}