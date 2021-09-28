package com.example.myfinance.data.dao


import androidx.room.*
import com.example.myfinance.data.models.PaymentTypeDB
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentTypeDAO  {

    @Insert
    suspend fun insertPaymentType(payments: PaymentTypeDB): Long

    @Query("SELECT * FROM PaymentTypeDB")
    fun getAllPaymentTypesFlow(): Flow<List<PaymentTypeDB>>

    @Query("SELECT * FROM PaymentTypeDB")
    suspend fun getAllPaymentTypes(): List<PaymentTypeDB>

    @Query("SELECT * FROM PaymentTypeDB WHERE id = :id")
    suspend fun getPaymentType(id: Long): PaymentTypeDB

    @Query("SELECT * FROM PaymentTypeDB WHERE id IN (:ids)")
    suspend fun getPaymentTypeByIds(ids: List<Long>): List<PaymentTypeDB>

    @Update
    suspend fun updatePaymentType(payments: PaymentTypeDB)

    @Delete
    suspend fun deletePaymentType(payments: PaymentTypeDB): Int

    @Query("DELETE FROM PaymentTypeDB WHERE id = :paymentType;")
    suspend fun deleteById(paymentType: Long): Int
}