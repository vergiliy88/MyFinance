package com.example.myfinance.ui.home.del_payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.repositories.PaymentRepositoryImpl
import com.example.myfinance.domain.models.PaymentJoinPaymentType
import com.example.myfinance.domain.usecase.payment.DelPayment
import com.example.myfinance.domain.usecase.payment.GetPayment
import com.example.myfinance.ui.base.BaseViewModal
import kotlinx.coroutines.launch

class DelPaymentViewModal  : BaseViewModal() {

    private val delPaymentUseCase = DelPayment(PaymentRepositoryImpl())

    private val getPaymentsUseCase = GetPayment(PaymentRepositoryImpl())
    private val _payments = MutableLiveData<List<PaymentJoinPaymentType>>().apply {
        value = listOf()
    }
    val payments: LiveData<List<PaymentJoinPaymentType>> = _payments
    var paymentsIds: MutableList<Long> = mutableListOf()

    fun getData(
        day: Int,
        month: Int,
        year: Int
    ) {
        paymentsIds.clear()
        viewModelScope.launch {
            _payments.value = getPaymentsUseCase.getByDateForEdit(day, month, year)
        }
    }

    fun onSelectForDelete(position: Int, isChecked: Boolean) {
        val paymentId = payments.value!![position].paymentId
        if (isChecked) {
            paymentId?.let {
                if (!paymentsIds.contains(it)) {
                    paymentsIds.add(it)
                }
            }
        } else {
            paymentsIds.remove(paymentId)
        }
    }

    fun updatePayments() {
        viewModelScope.launch {
            delPaymentUseCase.delPaymentsByIds(paymentsIds)
        }
    }
}
