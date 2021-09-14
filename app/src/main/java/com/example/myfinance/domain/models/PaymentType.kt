package com.example.myfinance.domain.models

import android.os.Parcel
import android.os.Parcelable

class PaymentType() : Parcelable {
    var id: Long? = null
    var name: String? = null
    var sum: Double? = null
    var color: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
        name = parcel.readString()
        sum = parcel.readValue(Double::class.java.classLoader) as? Double
        color = parcel.readString()
    }

    override fun describeContents(): Int {
        return  0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }

    companion object CREATOR : Parcelable.Creator<PaymentType> {
        override fun createFromParcel(parcel: Parcel): PaymentType {
            return PaymentType(parcel)
        }

        override fun newArray(size: Int): Array<PaymentType?> {
            return arrayOfNulls(size)
        }
    }
}