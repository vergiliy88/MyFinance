package com.example.myfinance.data.repositories

import com.example.myfinance.App
import com.example.myfinance.data.mappers.MapSettings
import com.example.myfinance.domain.models.Settings
import com.example.myfinance.domain.repositories.SettingsRepository

class SettingsRepositoryImpl: SettingsRepository {
    private val _db = App.getInstance().database.settingsDao()

    override suspend fun getSettings(): Settings {
        val list = _db.getAll()
        if (list.isEmpty()) {
            return Settings()
        }
        return MapSettings.mapFromDb(_db.getAll().first())
    }

    override suspend fun saveSettings(settings: Settings): Long {
        return _db.insert(MapSettings.mapTomDb(settings))
    }

    override suspend fun updateSettings(settings: Settings) {
        _db.update(MapSettings.mapTomDb(settings))
    }
}