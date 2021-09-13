package com.example.myfinance.data.dao

import androidx.room.*
import com.example.myfinance.data.entities.RegularPaymentsDB

@Dao
interface RegularPaymentsDAO {

    @Insert
    suspend fun insertRegularPayments(payments: RegularPaymentsDB): Long

    @Query("SELECT * FROM RegularPaymentsDB")
    suspend fun getAllRegularPayments(): List<RegularPaymentsDB>

    @Update
    suspend fun updateRegularPayments(payments: RegularPaymentsDB)

    @Delete
    suspend fun deleteRegularPayments(payments: RegularPaymentsDB)

}