package com.vergiliy.myfinance.domain.usecase.payment_types


import com.vergiliy.myfinance.domain.models.PaymentType
import com.vergiliy.myfinance.domain.repositories.PaymentTypesRepository
import kotlinx.coroutines.flow.Flow

class GetPaymentTypes(private val paymentTypeRepository: PaymentTypesRepository) {
    fun getAllFlow(): Flow<List<PaymentType>> {
        return paymentTypeRepository.getPaymentTypesFlow()
    }

    suspend fun getAll(): List<PaymentType> {
        return paymentTypeRepository.getPaymentTypeAll()
    }

    suspend fun getSingle(id: Long): PaymentType {
        return paymentTypeRepository.getPayment(id)
    }
}