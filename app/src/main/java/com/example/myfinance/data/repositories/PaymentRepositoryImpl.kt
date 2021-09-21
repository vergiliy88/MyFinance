package com.example.myfinance.data.repositories

import com.example.myfinance.App
import com.example.myfinance.data.mappers.MapPayment
import com.example.myfinance.domain.models.Payment
import com.example.myfinance.domain.repositories.PaymentRepository
import kotlinx.coroutines.flow.Flow

class PaymentRepositoryImpl: PaymentRepository {
    private var _db = App.getInstance().database.paymentDAO()
    override fun getPaymentsFlow(): Flow<List<Payment>> {
        val list = _db.getAllPaymentsFlow()
        return MapPayment.mapFromDbList(list)
    }

    override fun getPaymentsByDateFlow(month: String, year: String): Flow<List<Payment>> {
        return MapPayment.mapFromDbList(_db.getByDateFlow(month, year))
    }

    override suspend fun getPayment(id: Long): Payment {
        return MapPayment.mapFromDb(_db.getPayment(id))
    }

    override suspend fun getAllPayments(): List<Payment> {
        return MapPayment.mapFromDBList(_db.getAllPayments())
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
}