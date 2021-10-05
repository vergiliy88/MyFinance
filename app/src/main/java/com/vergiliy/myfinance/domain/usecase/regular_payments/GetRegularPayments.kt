package com.vergiliy.myfinance.domain.usecase.regular_payments


import com.vergiliy.myfinance.domain.models.RegularPayments
import com.vergiliy.myfinance.domain.repositories.RegularPaymentsRepository
import kotlinx.coroutines.flow.Flow

class GetRegularPayments(private val regularPaymentsRepository: RegularPaymentsRepository) {
    suspend fun getAll(): RegularPayments {
        return regularPaymentsRepository.getRegularPayments()
    }

    fun getAllFlow(): Flow<RegularPayments> {
        return regularPaymentsRepository.getRegularPaymentsFlow()
    }
}