package com.example.myfinance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myfinance.data.dao.PaymentDAO
import com.example.myfinance.data.dao.PaymentTypeDAO
import com.example.myfinance.data.dao.RegularPaymentsDAO
import com.example.myfinance.data.dao.SettingsDAO
import com.example.myfinance.data.models.PaymentDB
import com.example.myfinance.data.models.PaymentTypeDB
import com.example.myfinance.data.models.RegularPaymentsDB
import com.example.myfinance.data.models.SettingsDB


@Database(entities = [
                        RegularPaymentsDB::class,
                        PaymentTypeDB::class,
                        SettingsDB::class,
                        PaymentDB::class
                     ], version = 2)
abstract class DataBase : RoomDatabase() {
    abstract fun regularPaymentsDao(): RegularPaymentsDAO
    abstract fun paymentTypeDao(): PaymentTypeDAO
    abstract fun settingsDao(): SettingsDAO
    abstract fun paymentDAO(): PaymentDAO
}