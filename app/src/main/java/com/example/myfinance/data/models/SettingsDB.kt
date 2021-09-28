package com.example.myfinance.data.models


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SettingsDB {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var hourlyPayment: Boolean? = null
    var enabledComments: Boolean? = null
    var isReplayPayments: Boolean? = null
    var paymentReceived: Boolean? = null
}