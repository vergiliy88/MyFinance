package com.example.myfinance.domain.usecase.payment

import com.example.myfinance.domain.models.Payment
import com.example.myfinance.domain.repositories.PaymentRepository

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

    suspend fun saveAll (paymentList: List<Payment>){
        paymentRepository.savePayments(paymentList)
    }
}