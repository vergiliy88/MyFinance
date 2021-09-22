package com.example.myfinance.domain.usecase.regular_payments


import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.domain.repositories.RegularPaymentsRepository
import kotlinx.coroutines.flow.Flow

class GetRegularPayments(private val regularPaymentsRepository: RegularPaymentsRepository) {
    suspend fun getAll(): RegularPayments {
        return regularPaymentsRepository.getRegularPayments()
    }

    fun getAllFlow(): Flow<RegularPayments> {
        return regularPaymentsRepository.getRegularPaymentsFlow()
    }
}