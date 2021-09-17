package com.example.myfinance.data.dao


import androidx.room.*
import com.example.myfinance.data.entities.PaymentTypeDB
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentTypeDAO  {

    @Insert
    suspend fun insertPaymentType(payments: PaymentTypeDB): Long

    @Query("SELECT * FROM PaymentTypeDB")
    fun getAllPaymentTypes(): Flow<List<PaymentTypeDB>>

    @Update
    suspend fun updatePaymentType(payments: PaymentTypeDB)

    @Delete
    suspend fun deletePaymentType(payments: PaymentTypeDB): Int

    @Query("DELETE FROM PaymentTypeDB WHERE id = :paymentType;")
    suspend fun deleteById(paymentType: Long): Int
}