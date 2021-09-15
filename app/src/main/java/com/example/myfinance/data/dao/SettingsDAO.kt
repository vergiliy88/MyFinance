package com.example.myfinance.data.dao

import androidx.room.*
import com.example.myfinance.data.entities.SettingsDB

@Dao
interface SettingsDAO {
    @Insert
    suspend fun insert(settings: SettingsDB): Long

    @Query("SELECT * FROM SettingsDB")
    suspend fun getAll(): List<SettingsDB>

    @Update
    suspend fun update(settings: SettingsDB)

    @Delete
    suspend fun delete(settings: SettingsDB): Int

}