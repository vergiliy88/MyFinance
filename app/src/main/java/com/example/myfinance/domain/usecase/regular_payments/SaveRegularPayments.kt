package com.example.myfinance.domain.usecase.regular_payments

import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.domain.repositories.RegularPaymentsRepository

class SaveRegularPayments(private val regularPaymentsRepository: RegularPaymentsRepository) {
    suspend fun execute(regularPayments: RegularPayments): RegularPayments {
        return if (regularPayments.id != null) {
            regularPaymentsRepository.updateRegularPayments(regularPayments)
            regularPayments
        } else {
            val rowId = regularPaymentsRepository.saveRegularPayments(regularPayments)
            regularPayments.id = rowId
            regularPayments
        }
    }
}