package com.example.myfinance.data.repositories

import com.example.myfinance.App
import com.example.myfinance.data.DataBase
import com.example.myfinance.data.mappers.MapPaymentTypes
import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.repositories.PaymentTypesRepository

class PaymentTypesRepositoryImpl: PaymentTypesRepository {
    var db: DataBase = App.getInstance().database

    override suspend fun getPaymentTypes(): List<PaymentType> {
        val list = db.paymentTypeDao().getAllPaymentTypes()

        return MapPaymentTypes.mapFromDbList(list)
    }

    override suspend fun savePaymentType(paymentType: PaymentType): Long {
        return db.paymentTypeDao()
            .insertPaymentType(MapPaymentTypes.mapTomDb(paymentType))
    }

    override suspend fun updatePaymentType(paymentType: PaymentType) {
        db.paymentTypeDao()
            .updatePaymentType(MapPaymentTypes.mapTomDb(paymentType))
    }
}