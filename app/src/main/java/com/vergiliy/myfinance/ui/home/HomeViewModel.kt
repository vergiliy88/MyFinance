package com.vergiliy.myfinance.ui.home


import androidx.lifecycle.*
import com.vergiliy.myfinance.data.repositories.PaymentRepositoryImpl
import com.vergiliy.myfinance.data.repositories.PaymentTypesRepositoryImpl
import com.vergiliy.myfinance.data.repositories.RegularPaymentsRepositoryImpl
import com.vergiliy.myfinance.domain.models.Payment
import com.vergiliy.myfinance.domain.models.PaymentType
import com.vergiliy.myfinance.domain.models.RegularPayments
import com.vergiliy.myfinance.domain.usecase.payment.GetPayment
import com.vergiliy.myfinance.domain.usecase.payment_types.GetPaymentTypes
import com.vergiliy.myfinance.domain.usecase.regular_payments.GetRegularPayments
import com.vergiliy.myfinance.ui.base.BaseViewModal
import com.vergiliy.myfinance.ui.models.CalendarDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*


@ExperimentalCoroutinesApi
class HomeViewModel  : BaseViewModal() {
    private val calendar: Calendar = Calendar.getInstance();
    private val date = CalendarDate()
    private var job: Job? = null

    private val getPaymentUseCase = GetPayment(PaymentRepositoryImpl())
    private val getRegularPaymentsUseCase = GetRegularPayments(RegularPaymentsRepositoryImpl())
    private val getPaymentTypesUseCase = GetPaymentTypes(PaymentTypesRepositoryImpl())

    private val _currentDate = MutableLiveData<CalendarDate>().apply {
        date.month = calendar.get(Calendar.MONTH)
        date.year = calendar.get(Calendar.YEAR)
        value = date
    }

    private val _payments = MutableLiveData<List<Payment>>().apply {
        value = listOf()
    }

    private val _isShowLoading = MutableLiveData<Boolean>().apply {
        value = true
    }

    private val currentDate: LiveData<CalendarDate> = _currentDate

    val regularPayments: LiveData<RegularPayments> = getRegularPaymentsUseCase.getAllFlow().asLiveData()
    var paymentTypes: LiveData<List<PaymentType>> = getPaymentTypesUseCase.getAllFlow().asLiveData()
    var payments: LiveData<List<Payment>> = _payments
    var isShowLoading: LiveData<Boolean> = _isShowLoading

    init {
        subscribeOnPayments()
    }

    @ExperimentalCoroutinesApi
    fun setDate(month: Int, year: Int) {
        date.year = year
        date.month = month
        _currentDate.value = date
        subscribeOnPayments()
    }

    @ExperimentalCoroutinesApi
    fun subscribeOnPayments() {
        _isShowLoading.value = true
        job?.cancel()
        job = viewModelScope.launch {
            getPaymentUseCase.getByDateFlow(
                currentDate.value?.month!!,
                currentDate.value?.year!!
            ).collectLatest{
                _payments.value = it
                _isShowLoading.value = false
            }
        }
    }
}