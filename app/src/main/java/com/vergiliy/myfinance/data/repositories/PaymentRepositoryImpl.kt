package com.vergiliy.myfinance.data.repositories

import com.vergiliy.myfinance.App
import com.vergiliy.myfinance.data.mappers.MapPayment
import com.vergiliy.myfinance.domain.models.Payment
import com.vergiliy.myfinance.domain.models.PaymentJoinPaymentType
import com.vergiliy.myfinance.domain.models.PaymentStatistic
import com.vergiliy.myfinance.domain.repositories.PaymentRepository
import kotlinx.coroutines.flow.Flow

class PaymentRepositoryImpl: PaymentRepository {
    private var _db = App.getInstance().database.paymentDAO()
    override fun getPaymentsFlow(): Flow<List<Payment>> {
        val list = _db.getAllPaymentsFlow()
        return MapPayment.mapFromDbListFlow(list)
    }

    override fun getPaymentsByDateFlow(month: String, year: String): Flow<List<Payment>> {
        return MapPayment.mapFromDbListFlow(_db.getByDateFlow(month, year))
    }

    override fun getBetweenDateFlow(
        dateFrom: String,
        dateTo: String
    ): Flow<List<PaymentStatistic>> {
        return _db.getBetweenDateFlow(dateFrom, dateTo)
    }

    override suspend fun getPaymentsByDate(month: String, year: String): List<Payment> {
        return MapPayment.mapFromDBList(_db.getByDate(month, year))
    }

    override suspend fun getPayment(id: Long): Payment {
        return MapPayment.mapFromDb(_db.getPayment(id))
    }

    override suspend fun getAllPayments(): List<Payment> {
        return MapPayment.mapFromDBList(_db.getAllPayments())
    }

    override suspend fun getByDateForEdit(
        day: String,
        month: String,
        year: String
    ): List<PaymentJoinPaymentType> {
        return _db.getByDateForEdit(day, month, year)
    }

    override suspend fun savePayment(payment: Payment): Long {
        return _db.insertPayment(MapPayment.mapToDb(payment))
    }

    override suspend fun savePayments(payments: List<Payment>) {
        _db.insertPayments(MapPayment.mapToDbList(payments))
    }

    override suspend fun updatePayment(payment: Payment) {
        return _db.updatePayment(MapPayment.mapToDb(payment))
    }

    override suspend fun updatePayments(payments: List<Payment>) {
        _db.updatePayments(MapPayment.mapToDbList(payments))
    }

    override suspend fun delPayment(payment: Payment): Boolean {
        var result: Boolean
        _db.deletePayment(MapPayment.mapToDb(payment)).apply {
            result = this > 0
        }
        return result
    }

    override suspend fun delPaymentByTypeId(paymentTypeId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delPaymentsByIds(ids: List<Long>): Boolean {
        var result: Boolean
        _db.deleteByIds(ids).apply {
            result = this > 0
        }
        return result
    }
}