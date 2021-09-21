package com.example.myfinance.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.myfinance.data.repositories.PaymentRepositoryImpl
import com.example.myfinance.domain.models.Payment
import com.example.myfinance.domain.usecase.payment.DelPayment
import com.example.myfinance.domain.usecase.payment.GetPayment
import com.example.myfinance.ui.base.BaseViewModal
import java.util.*


class HomeViewModel  : BaseViewModal() {
    private val calendar: Calendar = Calendar.getInstance();

    private val getPaymentUseCase = GetPayment(PaymentRepositoryImpl())
    private val delPaymentUseCase = DelPayment(PaymentRepositoryImpl())

    private val _currentMoth = MutableLiveData<Int>().apply {
        value = calendar.get(Calendar.MONTH)
    }

    private val _currentYear = MutableLiveData<Int>().apply {
        value = calendar.get(Calendar.YEAR)
    }

    private val _payments = MutableLiveData<List<Payment>>().apply {
        value = null
    }

    val currentMoth: LiveData<Int> = _currentMoth
    val currentYear: LiveData<Int> = _currentYear
    var payments: LiveData<List<Payment>> = _payments

    init {
        getPaymentsByDate(currentMoth, currentYear)
    }


    fun setMonth(month: Int) {
        _currentMoth.value = month
    }

    fun setYear(year: Int) {
        _currentYear.value = year
    }

    private fun getPaymentsByDate(currentMoth: LiveData<Int>, currentYear: LiveData<Int>) {
        payments = getPaymentUseCase.
                    getByDateFlow(currentMoth.value!!, currentYear.value!!).asLiveData()
    }
}