package com.example.myfinance.domain.usecase.settings

import com.example.myfinance.domain.models.Settings
import com.example.myfinance.domain.repositories.SettingsRepository

class GetSettings(private val settingsRepository: SettingsRepository) {
    suspend fun execute(): Settings {
        return settingsRepository.getSettings()
    }
}