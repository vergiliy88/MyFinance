package com.vergiliy.myfinance.domain.usecase.payment

import com.vergiliy.myfinance.domain.models.Payment
import com.vergiliy.myfinance.domain.repositories.PaymentRepository

class DelPayment (private val paymentRepository: PaymentRepository) {
    suspend fun delPayment(payment: Payment): Boolean{
        return paymentRepository.delPayment(payment)
    }

    suspend fun delPaymentsByIds(ids: List<Long>): Boolean {
        return paymentRepository.delPaymentsByIds(ids)
    }
}