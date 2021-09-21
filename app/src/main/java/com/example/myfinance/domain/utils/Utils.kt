package com.example.myfinance.domain.utils

class Utils {
    companion object{
        fun convertMonthFromCal(month: Int): String {
            val monthSt = (month + 1).toString()
            return if(monthSt.length < 2) "0${monthSt}" else monthSt
        }
    }
}