package com.vergiliy.myfinance.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PaymentDB {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var comment: String? = null
    var realSum: Double? = null
    var paymentType: Long? = null
    var date: String? = null
}