package com.vergiliy.myfinance.domain.usecase.payment_types

import com.vergiliy.myfinance.domain.models.PaymentType
import com.vergiliy.myfinance.domain.repositories.PaymentTypesRepository

class DelPaymentTypes(private val paymentTypesRepository: PaymentTypesRepository) {
    suspend fun delPaymentType(paymentType: PaymentType, isDelFromCal: Boolean): Boolean{
        return paymentTypesRepository.delPaymentTypes(paymentType, isDelFromCal)
    }
}