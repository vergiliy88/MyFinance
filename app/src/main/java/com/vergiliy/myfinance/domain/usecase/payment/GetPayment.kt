package com.vergiliy.myfinance.domain.usecase.payment

import com.vergiliy.myfinance.domain.models.Payment
import com.vergiliy.myfinance.domain.models.PaymentJoinPaymentType
import com.vergiliy.myfinance.domain.models.PaymentStatistic
import com.vergiliy.myfinance.domain.models.StatisticDate
import com.vergiliy.myfinance.domain.repositories.PaymentRepository
import com.vergiliy.myfinance.domain.utils.Utils
import kotlinx.coroutines.flow.Flow

class GetPayment(private val paymentRepository: PaymentRepository) {
    fun getAllFlow(): Flow<List<Payment>> {
        return paymentRepository.getPaymentsFlow()
    }

    fun getByDateFlow(month: Int, year: Int): Flow<List<Payment>> {
        val monthStr = Utils.convertMonthFromCal(month)
        return paymentRepository.getPaymentsByDateFlow(monthStr, year.toString())
    }

    fun getBetweenDateFlow(month: Int, year: Int): Flow<List<Payment>> {
        val monthStr = Utils.convertMonthFromCal(month)
        return paymentRepository.getPaymentsByDateFlow(monthStr, year.toString())
    }

    fun getBetweenDateFlow(dateFrom: StatisticDate, dateTo: StatisticDate): Flow<List<PaymentStatistic>> {
        val dateFromStr = Utils.convertObjectDateToString(dateFrom)
        val dateToStr = Utils.convertObjectDateToString(dateTo)
        return paymentRepository.getBetweenDateFlow(dateFromStr, dateToStr)
    }

    suspend fun getByDate(month: Int, year: Int): List<Payment> {
        val monthStr = Utils.convertMonthFromCal(month)
        return paymentRepository.getPaymentsByDate(monthStr, year.toString())
    }

    suspend fun getAll(): List<Payment> {
        return paymentRepository.getAllPayments()
    }

    suspend fun getSingle(id: Long): Payment {
        return paymentRepository.getPayment(id)
    }

    suspend fun getByDateForEdit(day: Int, month: Int, year: Int): List<PaymentJoinPaymentType> {
        val monthStr = Utils.convertMonthFromCal(month)
        return paymentRepository.getByDateForEdit(day.toString(), monthStr, year.toString())
    }
}