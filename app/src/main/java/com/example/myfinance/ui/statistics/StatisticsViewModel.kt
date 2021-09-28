package com.example.myfinance.ui.statistics


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfinance.ui.base.BaseViewModal
import com.example.myfinance.ui.models.StatisticDate
import java.util.*



class StatisticsViewModel : BaseViewModal() {
    private val calendar: Calendar = Calendar.getInstance();

    private val _dateFrom = MutableLiveData<StatisticDate>().apply {
        val defaultDateFrom = StatisticDate()
        defaultDateFrom.year = calendar.get(Calendar.YEAR)
        defaultDateFrom.month = calendar.get(Calendar.MONTH) - 1
        defaultDateFrom.day = calendar.get(Calendar.DAY_OF_MONTH)
        value = defaultDateFrom
    }

    val dateFrom: LiveData<StatisticDate> = _dateFrom

    private val _dateTo = MutableLiveData<StatisticDate>().apply {
        val defaultDateFrom = StatisticDate()
        defaultDateFrom.year = calendar.get(Calendar.YEAR)
        defaultDateFrom.month = calendar.get(Calendar.MONTH)
        defaultDateFrom.day = calendar.get(Calendar.DAY_OF_MONTH)
        value = defaultDateFrom
    }

    val dateTo: LiveData<StatisticDate> = _dateTo

    fun setDateFrom(year: Int, month: Int, day: Int) {
        val newDate= StatisticDate()
        newDate.year = year
        newDate.month = month
        newDate.day = day
        _dateFrom.value = newDate
    }

    fun setDateTo(year: Int, month: Int, day: Int) {
        val newDate = StatisticDate()
        newDate.year = year
        newDate.month = month
        newDate.day = day
        _dateTo.value = newDate
    }
}