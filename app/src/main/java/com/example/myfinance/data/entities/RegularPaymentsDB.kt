package com.example.myfinance.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RegularPaymentsDB() {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0
    var salary: Double? = null
    var salaryDay: Int? = null
    var prepaid: Double? = null
    var prepaidDay: Int? = null
}