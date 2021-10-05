package com.vergiliy.myfinance.ui.statistics


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vergiliy.myfinance.data.repositories.PaymentRepositoryImpl
import com.vergiliy.myfinance.domain.models.PaymentStatistic
import com.vergiliy.myfinance.domain.models.StatisticDate
import com.vergiliy.myfinance.domain.usecase.payment.GetPayment
import com.vergiliy.myfinance.ui.base.BaseViewModal
import com.vergiliy.myfinance.ui.models.CalendarDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*



@ExperimentalCoroutinesApi
class StatisticsViewModel : BaseViewModal() {
    private val calendar: Calendar = Calendar.getInstance()
    private var job: Job? = null

    private val getPaymentsUseCase = GetPayment(PaymentRepositoryImpl())
    private val _payments = MutableLiveData<List<PaymentStatistic>>().apply {
        value = listOf()
    }
    val payments: LiveData<List<PaymentStatistic>> = _payments

    private val _dateFrom = MutableLiveData<CalendarDate>().apply {
        val defaultDateFrom = CalendarDate()
        defaultDateFrom.year = calendar.get(Calendar.YEAR)
        defaultDateFrom.month = calendar.get(Calendar.MONTH)
        defaultDateFrom.day = 1
        value = defaultDateFrom
    }

    val dateFrom: LiveData<CalendarDate> = _dateFrom

    private val _dateTo = MutableLiveData<CalendarDate>().apply {
        val defaultDateFrom = CalendarDate()
        defaultDateFrom.year = calendar.get(Calendar.YEAR)
        defaultDateFrom.month = calendar.get(Calendar.MONTH)
        defaultDateFrom.day = calendar.get(Calendar.DAY_OF_MONTH)
        value = defaultDateFrom
    }

    val dateTo: LiveData<CalendarDate> = _dateTo

    init {
        subscribeOnPayments()
    }

    @ExperimentalCoroutinesApi
    fun setDateFrom(year: Int, month: Int, day: Int) {
        val newDate= CalendarDate()
        newDate.year = year
        newDate.month = month
        newDate.day = day
        _dateFrom.value = newDate
    }

    @ExperimentalCoroutinesApi
    fun setDateTo(year: Int, month: Int, day: Int) {
        val newDate = CalendarDate()
        newDate.year = year
        newDate.month = month
        newDate.day = day
        _dateTo.value = newDate
    }


    fun applyFilter(){
        subscribeOnPayments()
    }

    @ExperimentalCoroutinesApi
    fun subscribeOnPayments() {
        job?.cancel()
        job = viewModelScope.launch {
            val dateFrom = StatisticDate(_dateFrom.value!!.year, _dateFrom.value!!.month, _dateFrom.value!!.day)
            val dateTo = StatisticDate(_dateTo.value!!.year, _dateTo.value!!.month, _dateTo.value!!.day)
            getPaymentsUseCase.getBetweenDateFlow(dateFrom, dateTo)
                .collectLatest{
                _payments.value = it
            }
        }
    }
}