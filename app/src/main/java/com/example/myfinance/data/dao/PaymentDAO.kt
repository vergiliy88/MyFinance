package com.example.myfinance.data.dao

import androidx.room.*
import com.example.myfinance.data.entities.PaymentDB
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDAO {
    @Insert
    suspend fun insertPayment(payments: PaymentDB): Long

    @Insert
    suspend fun insertPayments(payments: List<PaymentDB>)

    @Query("SELECT * FROM PaymentDB")
    fun getAllPaymentsFlow(): Flow<List<PaymentDB>>

    @Query("SELECT * FROM PaymentDB WHERE strftime('%m',date) = :month AND strftime('%Y',date) = :year")
    fun getByDateFlow(month: String, year: String): Flow<List<PaymentDB>>

    @Query("SELECT * FROM PaymentDB WHERE strftime('%m',date) = :month AND strftime('%Y',date) = :year")
    suspend fun getByDate(month: String, year: String): List<PaymentDB>

    @Query("SELECT * FROM PaymentDB")
    suspend fun getAllPayments(): List<PaymentDB>

    @Query("SELECT * FROM PaymentDB WHERE id = :id")
    suspend fun getPayment(id: Long): PaymentDB

    @Query("SELECT * FROM PaymentDB WHERE id IN (:ids)")
    suspend fun getPaymentIds(ids: List<Long>): List<PaymentDB>

    @Update
    suspend fun updatePayment(payments: PaymentDB)

    @Delete
    suspend fun deletePayment(payments: PaymentDB): Int

    @Query("DELETE FROM PaymentDB WHERE id = :paymentId;")
    suspend fun deleteById(paymentId: Long): Int

    @Query("DELETE FROM PaymentDB WHERE paymentType = :paymentTypeId;")
    suspend fun deletePaymentByPaymentTypeId(paymentTypeId: Long): Int
}