package com.example.myfinance.data.repositories

import com.example.myfinance.App
import com.example.myfinance.data.DataBase
import com.example.myfinance.data.mappers.MapPaymentTypes
import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.repositories.PaymentTypesRepository

class PaymentTypesRepositoryImpl: PaymentTypesRepository {
    private var _db: DataBase = App.getInstance().database

    override suspend fun getPaymentTypes(): List<PaymentType> {
        val list = _db.paymentTypeDao().getAllPaymentTypes()
        return MapPaymentTypes.mapFromDbList(list)
    }

    override suspend fun savePaymentType(paymentType: PaymentType): Long {
        return _db.paymentTypeDao()
            .insertPaymentType(MapPaymentTypes.mapTomDb(paymentType))
    }

    override suspend fun updatePaymentType(paymentType: PaymentType) {
        _db.paymentTypeDao()
            .updatePaymentType(MapPaymentTypes.mapTomDb(paymentType))
    }

    override suspend fun delPaymentTypes(paymentType: PaymentType, isDelFromCal: Boolean): Boolean {
        var result: Boolean
        _db.paymentTypeDao().deletePaymentType(MapPaymentTypes.mapTomDb(paymentType)).apply {
            result = this > 0
        }

        return result
    }
}