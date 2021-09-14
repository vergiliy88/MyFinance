package com.example.myfinance.domain.repositories


import com.example.myfinance.domain.models.RegularPayments

interface RegularPaymentsRepository {
    suspend fun saveRegularPayments(regularPayments: RegularPayments): Long
    suspend fun getRegularPayments(): RegularPayments
    suspend fun updateRegularPayments(regularPayments: RegularPayments)
}