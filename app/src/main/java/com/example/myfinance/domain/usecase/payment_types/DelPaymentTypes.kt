package com.example.myfinance.domain.usecase.payment_types

import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.repositories.PaymentTypesRepository

class DelPaymentTypes(private val paymentTypesRepository: PaymentTypesRepository) {
    suspend fun delPaymentType(paymentType: PaymentType, isDelFromCal: Boolean): Boolean{
        return paymentTypesRepository.delPaymentTypes(paymentType, isDelFromCal)
    }
}