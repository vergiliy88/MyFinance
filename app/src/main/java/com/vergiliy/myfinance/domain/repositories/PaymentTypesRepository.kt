package com.vergiliy.myfinance.domain.repositories


import com.vergiliy.myfinance.domain.models.PaymentType
import kotlinx.coroutines.flow.Flow

interface PaymentTypesRepository {
    fun getPaymentTypesFlow(): Flow<List<PaymentType>>
    suspend fun getPaymentTypeAll(): List<PaymentType>
    suspend fun getPayment(id: Long): PaymentType
    suspend fun getPaymentTypeByIds(ids: List<Long>): List<PaymentType>
    suspend fun savePaymentType(paymentType: PaymentType): Long
    suspend fun updatePaymentType(paymentType: PaymentType)
    suspend fun delPaymentTypes(paymentTypeId: PaymentType, isDelFromCal: Boolean): Boolean
}