package com.vergiliy.myfinance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vergiliy.myfinance.data.dao.PaymentDAO
import com.vergiliy.myfinance.data.dao.PaymentTypeDAO
import com.vergiliy.myfinance.data.dao.RegularPaymentsDAO
import com.vergiliy.myfinance.data.dao.SettingsDAO
import com.vergiliy.myfinance.data.models.PaymentDB
import com.vergiliy.myfinance.data.models.PaymentTypeDB
import com.vergiliy.myfinance.data.models.RegularPaymentsDB
import com.vergiliy.myfinance.data.models.SettingsDB


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