package com.example.myfinance.domain.usecase.settings

import com.example.myfinance.domain.models.Settings
import com.example.myfinance.domain.repositories.SettingsRepository

class SaveSettings(private val settingsRepository: SettingsRepository) {
    suspend fun execute(settings: Settings): Settings {
        return if (settings.id != null) {
            settingsRepository.updateSettings(settings)
            settings
        } else {
            val rowId = settingsRepository.saveSettings(settings)
            settings.id = rowId
            settings
        }
    }
}