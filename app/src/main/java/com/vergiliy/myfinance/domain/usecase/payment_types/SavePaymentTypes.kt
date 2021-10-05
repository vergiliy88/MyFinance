package com.vergiliy.myfinance.domain.usecase.payment_types

import com.vergiliy.myfinance.domain.models.PaymentType
import com.vergiliy.myfinance.domain.repositories.PaymentTypesRepository

class SavePaymentTypes (private val paymentTypesRepository: PaymentTypesRepository) {
    suspend fun execute(paymentType: PaymentType): PaymentType {
        return if (paymentType.id != null) {
            paymentTypesRepository.updatePaymentType(paymentType)
            paymentType
        } else {
            val rowId = paymentTypesRepository.savePaymentType(paymentType)
            paymentType.id = rowId
            paymentType
        }
    }
}