package com.example.myfinance.ui.models

import android.os.Parcel
import android.os.Parcelable

class CalendarDate() : Parcelable {
    var year: Int? = null
    var month: Int? = null
    var day: Int?  = null

    constructor(parcel: Parcel) : this() {
        year = parcel.readValue(Int::class.java.classLoader) as? Int
        month = parcel.readValue(Int::class.java.classLoader) as? Int
        day = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(year)
        parcel.writeValue(month)
        parcel.writeValue(day)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CalendarDate> {
        override fun createFromParcel(parcel: Parcel): CalendarDate {
            return CalendarDate(parcel)
        }

        override fun newArray(size: Int): Array<CalendarDate?> {
            return arrayOfNulls(size)
        }
    }
}