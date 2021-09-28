package com.example.myfinance.ui.home.add_payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.repositories.PaymentRepositoryImpl
import com.example.myfinance.data.repositories.PaymentTypesRepositoryImpl
import com.example.myfinance.domain.models.Payment
import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.usecase.payment.SavePayment
import com.example.myfinance.domain.usecase.payment_types.GetPaymentTypes
import com.example.myfinance.domain.utils.Utils.Companion.convertMonthFromCal
import com.example.myfinance.ui.base.BaseViewModal
import com.example.myfinance.ui.models.PaymentTemplate
import com.example.myfinance.ui.models.StatisticDate
import com.example.myfinance.utils.Utils
import kotlinx.coroutines.*
import java.util.*

class AddPaymentViewModel: BaseViewModal() {

    private val savePaymentUseCase = SavePayment(PaymentRepositoryImpl())
    private val calendar: Calendar = Calendar.getInstance();
    private val getPaymentTypesUseCase = GetPaymentTypes(PaymentTypesRepositoryImpl())
    private val _paymentTypes = MutableLiveData<List<PaymentType>>().apply {
        value = listOf()
    }
    val paymentTypes: LiveData<List<PaymentType>> = _paymentTypes
    var paymentTemplate: MutableList<PaymentTemplate> = mutableListOf()

    private val _dateFrom = MutableLiveData<StatisticDate>().apply {
        val defaultDateFrom = StatisticDate()
        defaultDateFrom.year = calendar.get(Calendar.YEAR)
        defaultDateFrom.month = calendar.get(Calendar.MONTH)
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

    fun setPaymentTemplateSelected(position: Int) {
        val list = paymentTemplate
        list[position].isSelected = list[position].isSelected != true
    }

    fun setCommentPaymentTemplate(position: Int, value: String) {
        viewModelScope.launch {
            val list = paymentTemplate
            list[position].comment = value
        }
    }

    fun setSumPaymentTemplate(position: Int, value: Double) {
        viewModelScope.launch {
            val list = paymentTemplate
            list[position].realSum = value
        }
    }

    fun savePayments() {
        val dateFromStr = "${dateFrom.value?.year}-${convertMonthFromCal(dateFrom.value?.month!!)}-${dateFrom.value?.day}"
        val dateToStr = "${dateTo.value?.year}-${convertMonthFromCal(dateTo.value?.month!!)}-${dateTo.value?.day}"
        val dateList = Utils.getRangeDates(dateFromStr, dateToStr)
        val resultList: MutableList<Payment> = mutableListOf()
        for (itemPayment in paymentTemplate) {
            if (itemPayment.isSelected) {
                for (date in dateList) {
                    val payment = Payment()
                    var realSum =  itemPayment.sum
                    if (itemPayment.realSum != null && itemPayment.realSum != 0.0) {
                        realSum = itemPayment.realSum
                    }
                    payment.comment = itemPayment.comment
                    payment.paymentType = itemPayment.paymentType
                    payment.realSum = realSum
                    payment.date = date
                    resultList.add(payment)
                }
            }
        }
        viewModelScope.launch {
            savePaymentUseCase.saveAll(resultList)
        }
    }

    fun generateList() {
        viewModelScope.launch {
            val listPaymentTypes = getPaymentTypesUseCase.getAll()
            val listPaymentTemplate: MutableList<PaymentTemplate> = mutableListOf()
            listPaymentTypes.forEach {
                val paymentTemplate = PaymentTemplate()
                paymentTemplate.paymentType = it.id
                paymentTemplate.color = it.color
                paymentTemplate.paymentParam = it.name
                paymentTemplate.sum = it.sum
                listPaymentTemplate.add(paymentTemplate)
            }
            paymentTemplate = listPaymentTemplate
            _paymentTypes.value = listPaymentTypes
        }
    }
}