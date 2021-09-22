package com.example.myfinance.domain.repositories


import com.example.myfinance.domain.models.RegularPayments
import kotlinx.coroutines.flow.Flow

interface RegularPaymentsRepository {
    suspend fun saveRegularPayments(regularPayments: RegularPayments): Long
    suspend fun getRegularPayments(): RegularPayments
    fun getRegularPaymentsFlow(): Flow<RegularPayments>
    suspend fun updateRegularPayments(regularPayments: RegularPayments)
}