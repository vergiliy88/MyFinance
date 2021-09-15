package com.example.myfinance.domain.repositories


import com.example.myfinance.domain.models.PaymentType

interface PaymentTypesRepository {
    suspend fun getPaymentTypes(): List<PaymentType>
    suspend fun savePaymentType(paymentType: PaymentType): Long
    suspend fun updatePaymentType(paymentType: PaymentType)
    suspend fun delPaymentTypes(paymentTypeId: PaymentType, isDelFromCal: Boolean): Boolean
}