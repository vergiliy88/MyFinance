package com.example.myfinance.domain.models

import androidx.room.Entity

@Entity
data class PaymentJoinPaymentType (
    var paymentId: Long? = null,
    var paymentTypeId: Long? = null,
    var sum: Double? = null,
    var realSum: Double? = null,
    var comment: String? = null,
    var date: String? = null,
    var paymentTypeName: String? = null,
    var paymentColor: String? = null
)