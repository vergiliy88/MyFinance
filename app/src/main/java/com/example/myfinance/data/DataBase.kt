package com.example.myfinance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myfinance.data.dao.PaymentTypeDAO
import com.example.myfinance.data.dao.RegularPaymentsDAO
import com.example.myfinance.data.dao.SettingsDAO
import com.example.myfinance.data.entities.PaymentTypeDB
import com.example.myfinance.data.entities.RegularPaymentsDB
import com.example.myfinance.data.entities.SettingsDB


@Database(entities = [RegularPaymentsDB::class,
                      PaymentTypeDB::class,
                      SettingsDB::class], version = 2)
abstract class DataBase : RoomDatabase() {
    abstract fun regularPaymentsDao(): RegularPaymentsDAO
    abstract fun paymentTypeDao(): PaymentTypeDAO
    abstract fun settingsDao(): SettingsDAO
}