package com.example.myfinance.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        fun getRangeDates(dateStart: String, dateEnd: String) : List<String> {
            val dates = ArrayList<String>()
            val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd")

            var date1: Date? = null
            var date2: Date? = null

            try {
                date1 = df1.parse(dateStart)
                date2 = df1.parse(dateEnd)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val cal1 = Calendar.getInstance()
            cal1.time = date1!!


            val cal2 = Calendar.getInstance()
            cal2.time = date2!!

            while (!cal1.after(cal2)) {
                val pattern = "yyyy-MM-dd"
                val simpleDateFormat = SimpleDateFormat(pattern)
                val date = simpleDateFormat.format(cal1.time)
                dates.add(date)
                cal1.add(Calendar.DATE, 1)
            }
            return dates
        }
    }
}