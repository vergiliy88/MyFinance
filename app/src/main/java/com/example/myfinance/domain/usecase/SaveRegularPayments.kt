package com.example.myfinance.domain.usecase

import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.domain.repositories.RegularPaymentsRepository

class SaveRegularPayments(private val regularPaymentsRepository: RegularPaymentsRepository) {
    suspend fun execute(regularPayments: RegularPayments): RegularPayments {
        return regularPaymentsRepository.saveRegularPayments(regularPayments)
    }
}