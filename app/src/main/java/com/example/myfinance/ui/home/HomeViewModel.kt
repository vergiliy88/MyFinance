package com.example.myfinance.ui.home


import androidx.lifecycle.*
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
import com.example.myfinance.ui.entities.StatisticDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*


class HomeViewModel  : BaseViewModal() {
    private val calendar: Calendar = Calendar.getInstance();
    private val date = StatisticDate()
    private var job: Job? = null

    private val getPaymentUseCase = GetPayment(PaymentRepositoryImpl())
    private val getRegularPaymentsUseCase = GetRegularPayments(RegularPaymentsRepositoryImpl())
    private val getPaymentTypesUseCase = GetPaymentTypes(PaymentTypesRepositoryImpl())

    private val _currentDate = MutableLiveData<StatisticDate>().apply {
        date.month = calendar.get(Calendar.MONTH)
        date.year = calendar.get(Calendar.YEAR)
        value = date
    }

    private val _payments = MutableLiveData<List<Payment>>().apply {
        value = listOf()
    }

    private val currentDate: LiveData<StatisticDate> = _currentDate

    val regularPayments: LiveData<RegularPayments> = getRegularPaymentsUseCase.getAllFlow().asLiveData()
    var paymentTypes: LiveData<List<PaymentType>> = getPaymentTypesUseCase.getAllFlow().asLiveData()
    var payments: LiveData<List<Payment>> = _payments

    init {
        subscribeOnPayments()
    }

    fun setDate(month: Int, year: Int) {
        date.year = year
        date.month = month
        _currentDate.value = date
        subscribeOnPayments()
    }

    @ExperimentalCoroutinesApi
    fun subscribeOnPayments() {
        job?.cancel()
        job = viewModelScope.launch {
            getPaymentUseCase.getByDateFlow(
                currentDate.value?.month!!,
                currentDate.value?.year!!
            ).collectLatest{
                _payments.value = it
            }
        }
    }
}