package com.example.myfinance.domain.usecase.payment

import com.example.myfinance.domain.models.Payment
import com.example.myfinance.domain.repositories.PaymentRepository

class DelPayment (private val paymentRepository: PaymentRepository) {
    suspend fun delPayment(payment: Payment): Boolean{
        return paymentRepository.delPayment(payment)
    }
}