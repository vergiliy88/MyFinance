package com.example.myfinance.domain.usecase.payment_types


import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.repositories.PaymentTypesRepository
import kotlinx.coroutines.flow.Flow

class GetPaymentTypes(private val paymentTypeRepository: PaymentTypesRepository) {
    fun execute(): Flow<List<PaymentType>> {
        return paymentTypeRepository.getPaymentTypes()
    }
}