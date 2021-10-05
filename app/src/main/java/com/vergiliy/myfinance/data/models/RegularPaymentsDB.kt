package com.vergiliy.myfinance.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RegularPaymentsDB() {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var salary: Double? = null
    var salaryDay: Int? = null
    var prepaid: Double? = null
    var prepaidDay: Int? = null
}