package com.example.myfinance.domain.utils

import com.example.myfinance.domain.models.StatisticDate

class Utils {
    companion object{
        fun convertMonthFromCal(month: Int): String {
            val monthSt = (month + 1).toString()
            return if(monthSt.length < 2) "0${monthSt}" else monthSt
        }

        fun convertObjectDateToString(date: StatisticDate): String {
            var monthSt = (date.month?.plus(1)).toString()
            monthSt = if(monthSt.length < 2) "0${monthSt}" else monthSt
            return "${date.year!!}-${monthSt}-${date.day!!}"
        }
    }
}