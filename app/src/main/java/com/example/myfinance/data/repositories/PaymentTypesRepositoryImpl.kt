package com.example.myfinance.data.repositories

import com.example.myfinance.App
import com.example.myfinance.data.mappers.MapPaymentTypes
import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.repositories.PaymentTypesRepository
import kotlinx.coroutines.flow.Flow

class PaymentTypesRepositoryImpl: PaymentTypesRepository {
    private var _db = App.getInstance().database.paymentTypeDao()

    override fun getPaymentTypes(): Flow<List<PaymentType>> {
        val list = _db.getAllPaymentTypes()
        return MapPaymentTypes.mapFromDbList(list)
    }

    override suspend fun savePaymentType(paymentType: PaymentType): Long {
        return _db.insertPaymentType(MapPaymentTypes.mapTomDb(paymentType))
    }

    override suspend fun updatePaymentType(paymentType: PaymentType) {
        _db.updatePaymentType(MapPaymentTypes.mapTomDb(paymentType))
    }

    override suspend fun delPaymentTypes(paymentType: PaymentType, isDelFromCal: Boolean): Boolean {
        var result: Boolean
        _db.deletePaymentType(MapPaymentTypes.mapTomDb(paymentType)).apply {
            result = this > 0
        }
        return result
    }
}