package com.example.myfinance.domain.usecase

import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.repositories.PaymentTypesRepository

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