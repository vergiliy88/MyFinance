package com.example.myfinance.ui.home.view_edit_payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.repositories.PaymentRepositoryImpl
import com.example.myfinance.domain.models.Payment
import com.example.myfinance.domain.models.PaymentJoinPaymentType
import com.example.myfinance.domain.usecase.payment.GetPayment
import com.example.myfinance.domain.usecase.payment.SavePayment
import com.example.myfinance.ui.base.BaseViewModal
import kotlinx.coroutines.launch

class ViewEditPaymentViewModel : BaseViewModal() {

    private val savePaymentUseCase = SavePayment(PaymentRepositoryImpl())

    private val getPaymentsUseCase = GetPayment(PaymentRepositoryImpl())
    private val _payments = MutableLiveData<List<PaymentJoinPaymentType>>().apply {
        value = listOf()
    }
    val payments: LiveData<List<PaymentJoinPaymentType>> = _payments
    var paymentTemplates: MutableMap<Long, Payment> = mutableMapOf()

    fun getData(
                day: Int,
                month: Int,
                year: Int
    ) {
        viewModelScope.launch {
            _payments.value = getPaymentsUseCase.getByDateForEdit(day, month, year)

        }
    }

    fun setCommentPaymentTemplate(position: Int, value: String) {
        val paymentTemplate = payments.value?.get(position)
        payments.value?.get(position)?.comment = value
        val paymentId = paymentTemplate?.paymentId!!
        if (paymentTemplates.containsKey(paymentId)) {
            paymentTemplates[paymentId]!!.comment = value
        } else {
            val payment = Payment()
            payment.id = paymentTemplate.paymentId
            payment.paymentType = paymentTemplate.paymentTypeId
            payment.date = paymentTemplate.date
            payment.comment = value
            var realSum =  paymentTemplate.sum
            if (paymentTemplate.realSum != null && paymentTemplate.realSum != 0.0) {
                realSum = paymentTemplate.realSum
            }
            payment.realSum = realSum
            paymentTemplates[paymentId] = payment
        }
    }

    fun setSumPaymentTemplate(position: Int, value: Double) {
        val paymentTemplate = payments.value?.get(position)
        payments.value?.get(position)?.realSum = value
        val paymentId = paymentTemplate?.paymentId!!
        if (paymentTemplates.containsKey(paymentId)) {
            var realSum =  paymentTemplate.realSum
            if (value != null && value != 0.0) {
                realSum = paymentTemplate.realSum
            }
            paymentTemplates[paymentId]!!.realSum = realSum
        } else {
            val payment = Payment()
            payment.id = paymentTemplate.paymentId
            payment.paymentType = paymentTemplate.paymentTypeId
            payment.date = paymentTemplate.date
            payment.comment = paymentTemplate.comment
            var realSum =  paymentTemplate.realSum
            if (value != null && value != 0.0) {
                realSum = paymentTemplate.realSum
            }
            payment.realSum = realSum
            paymentTemplates[paymentId] = payment
        }
    }

    fun updatePayments() {
        val result = paymentTemplates.values.toList()
        viewModelScope.launch {
            savePaymentUseCase.updatePayments(result)
        }
    }
}
