package com.example.myfinance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myfinance.data.dao.RegularPaymentsDAO
import com.example.myfinance.data.entities.RegularPaymentsDB


@Database(entities = [RegularPaymentsDB::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun regularPaymentsDao(): RegularPaymentsDAO
}