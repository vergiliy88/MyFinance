package com.example.myfinance.domain.usecase.regular_payments


import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.domain.repositories.RegularPaymentsRepository

class GetRegularPayments(private val regularPaymentsRepository: RegularPaymentsRepository) {
    suspend fun execute(): RegularPayments {
        return regularPaymentsRepository.getRegularPayments()
    }
}