package com.vergiliy.myfinance.domain.usecase.payment

import com.vergiliy.myfinance.domain.models.Payment
import com.vergiliy.myfinance.domain.repositories.PaymentRepository

class SavePayment (private val paymentRepository: PaymentRepository) {
    suspend fun execute(payment: Payment): Payment {
        return if (payment.id != null) {
            paymentRepository.updatePayment(payment)
            payment
        } else {
            val rowId = paymentRepository.savePayment(payment)
            payment.id = rowId
            payment
        }
    }

    suspend fun saveAll (payments: List<Payment>){
        paymentRepository.savePayments(payments)
    }

    suspend fun updatePayments(payments: List<Payment>) {
        paymentRepository.updatePayments(payments)
    }
}