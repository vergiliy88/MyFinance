package com.example.myfinance.domain.repositories

import com.example.myfinance.domain.models.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentsFlow(): Flow<List<Payment>>
    fun getPaymentsByDateFlow(month: String, year: String): Flow<List<Payment>>
    suspend fun getPayment(id: Long): Payment
    suspend fun getAllPayments(): List<Payment>
    suspend fun savePayment(payment: Payment): Long
    suspend fun savePayments(payments: List<Payment>)
    suspend fun updatePayment(payment: Payment)
    suspend fun delPayment(payment: Payment): Boolean
    suspend fun delPaymentByTypeId(paymentTypeId: Long): Boolean
}