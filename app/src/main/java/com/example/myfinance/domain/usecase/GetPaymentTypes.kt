package com.example.myfinance.domain.usecase


import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.repositories.PaymentTypesRepository

class GetPaymentTypes(private val paymentTypeRepository: PaymentTypesRepository) {
    suspend fun execute(): List<PaymentType> {
        val list = paymentTypeRepository.getPaymentTypes()
        if (list.isEmpty()) {
            return listOf()
        }
        return list
    }
}