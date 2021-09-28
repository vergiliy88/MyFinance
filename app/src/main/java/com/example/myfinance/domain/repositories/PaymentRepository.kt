package com.example.myfinance.domain.repositories

import com.example.myfinance.domain.models.Payment
import com.example.myfinance.domain.models.PaymentJoinPaymentType
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentsFlow(): Flow<List<Payment>>
    fun getPaymentsByDateFlow(month: String, year: String): Flow<List<Payment>>
    suspend fun getPaymentsByDate(month: String, year: String):List<Payment>
    suspend fun getPayment(id: Long): Payment
    suspend fun getAllPayments(): List<Payment>
    suspend fun getByDateForEdit(day: String, month: String, year: String): List<PaymentJoinPaymentType>
    suspend fun savePayment(payment: Payment): Long
    suspend fun savePayments(payments: List<Payment>)
    suspend fun updatePayment(payment: Payment)
    suspend fun updatePayments(payments: List<Payment>)
    suspend fun delPayment(payment: Payment): Boolean
    suspend fun delPaymentByTypeId(paymentTypeId: Long): Boolean
}