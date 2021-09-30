package com.example.myfinance.domain.models

import androidx.room.Entity

@Entity
class PaymentStatistic (
    var realSum: Double? = null,
    var paymentTypeName: String? = null,
    var paymentColor: String? = null
)