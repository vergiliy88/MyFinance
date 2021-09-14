package com.example.myfinance.data.dao

import androidx.room.*
import com.example.myfinance.data.entities.PaymentTypeDB

@Dao
interface PaymentTypeDAO  {

    @Insert
    suspend fun insertPaymentType(payments: PaymentTypeDB): Long

    @Query("SELECT * FROM PaymentTypeDB")
    suspend fun getAllPaymentTypes(): List<PaymentTypeDB>

    @Update
    suspend fun updatePaymentType(payments: PaymentTypeDB)

    @Delete
    suspend fun deletePaymentType(payments: PaymentTypeDB)

}