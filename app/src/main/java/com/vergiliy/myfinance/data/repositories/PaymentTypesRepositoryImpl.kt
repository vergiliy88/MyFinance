package com.vergiliy.myfinance.data.repositories

import com.vergiliy.myfinance.App
import com.vergiliy.myfinance.data.mappers.MapPaymentTypes
import com.vergiliy.myfinance.domain.models.PaymentType
import com.vergiliy.myfinance.domain.repositories.PaymentTypesRepository
import kotlinx.coroutines.flow.Flow

class PaymentTypesRepositoryImpl: PaymentTypesRepository {
    private var _dbPaymentType = App.getInstance().database.paymentTypeDao()
    private var _dbPayment = App.getInstance().database.paymentDAO()

    override fun getPaymentTypesFlow(): Flow<List<PaymentType>> {
        val list = _dbPaymentType.getAllPaymentTypesFlow()
        return MapPaymentTypes.mapFromDBFlowList(list)
    }

    override suspend fun getPaymentTypeAll(): List<PaymentType> {
        return MapPaymentTypes.mapFromDBList(_dbPaymentType.getAllPaymentTypes())
    }

    override suspend fun getPayment(id: Long): PaymentType {
        return MapPaymentTypes.mapFromDb(_dbPaymentType.getPaymentType(id))
    }

    override suspend fun getPaymentTypeByIds(ids: List<Long>): List<PaymentType> {
        return MapPaymentTypes.mapFromDBList(_dbPaymentType.getPaymentTypeByIds(ids))
    }

    override suspend fun savePaymentType(paymentType: PaymentType): Long {
        return _dbPaymentType.insertPaymentType(MapPaymentTypes.mapToDb(paymentType))
    }

    override suspend fun updatePaymentType(paymentType: PaymentType) {
        _dbPaymentType.updatePaymentType(MapPaymentTypes.mapToDb(paymentType))
    }

    override suspend fun delPaymentTypes(paymentType: PaymentType, isDelFromCal: Boolean): Boolean {
        var result: Boolean
        _dbPaymentType.deletePaymentType(MapPaymentTypes.mapToDb(paymentType)).apply {
            result = this > 0
            _dbPayment.deletePaymentByPaymentTypeId(paymentType.id!!)
        }
        return result
    }
}