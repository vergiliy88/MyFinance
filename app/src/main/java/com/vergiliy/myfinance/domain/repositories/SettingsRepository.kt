package com.vergiliy.myfinance.domain.repositories

import com.vergiliy.myfinance.domain.models.Settings

interface SettingsRepository {
    suspend fun getSettings(): Settings
    suspend fun saveSettings(settings: Settings): Long
    suspend fun updateSettings(settings: Settings)
}