package com.example.myfinance.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.myfinance.data.repositories.PaymentRepositoryImpl
import com.example.myfinance.data.repositories.PaymentTypesRepositoryImpl
import com.example.myfinance.data.repositories.RegularPaymentsRepositoryImpl
import com.example.myfinance.domain.models.Payment
import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.domain.usecase.payment.GetPayment
import com.example.myfinance.domain.usecase.payment_types.GetPaymentTypes
import com.example.myfinance.domain.usecase.regular_payments.GetRegularPayments
import com.example.myfinance.ui.base.BaseViewModal
import java.util.*


class HomeViewModel  : BaseViewModal() {
    private val calendar: Calendar = Calendar.getInstance();

    private val getPaymentUseCase = GetPayment(PaymentRepositoryImpl())
    private val getRegularPaymentsUseCase = GetRegularPayments(RegularPaymentsRepositoryImpl())
    private val getPaymentTypesUseCase = GetPaymentTypes(PaymentTypesRepositoryImpl())

    private val _currentMoth = MutableLiveData<Int>().apply {
        value = calendar.get(Calendar.MONTH)
    }

    private val _currentYear = MutableLiveData<Int>().apply {
        value = calendar.get(Calendar.YEAR)
    }

    private val currentMoth: LiveData<Int> = _currentMoth
    private val currentYear: LiveData<Int> = _currentYear


    val regularPayments: LiveData<RegularPayments> = getRegularPaymentsUseCase.getAllFlow().asLiveData()
    var paymentTypes: LiveData<List<PaymentType>> = getPaymentTypesUseCase.getAllFlow().asLiveData()
    var payments: LiveData<List<Payment>> = getPaymentUseCase.
                                                getByDateFlow(currentMoth.value!!, currentYear.value!!).asLiveData()

    fun setMonth(month: Int) {
        _currentMoth.value = month
    }

    fun setYear(year: Int) {
        _currentYear.value = year
    }
}