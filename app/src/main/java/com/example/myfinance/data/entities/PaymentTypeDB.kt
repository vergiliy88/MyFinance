package com.example.myfinance.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PaymentTypeDB {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var name: String? = null
    var sum: Double? = null
    var color: String? = null
}