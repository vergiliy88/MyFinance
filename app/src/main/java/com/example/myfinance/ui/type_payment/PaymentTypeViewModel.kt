package com.example.myfinance.ui.type_payment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.repositories.PaymentTypesRepositoryImpl
import com.example.myfinance.data.repositories.RegularPaymentsRepositoryImpl
import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.domain.usecase.payment_types.DelPaymentTypes
import com.example.myfinance.domain.usecase.payment_types.GetPaymentTypes
import com.example.myfinance.domain.usecase.payment_types.SavePaymentTypes
import com.example.myfinance.domain.usecase.regular_payments.GetRegularPayments
import com.example.myfinance.domain.usecase.regular_payments.SaveRegularPayments
import com.example.myfinance.ui.base.BaseViewModal
import kotlinx.coroutines.launch


class PaymentTypeViewModel: BaseViewModal() {

    private val saveRegularPaymentsUseCase = SaveRegularPayments(RegularPaymentsRepositoryImpl())
    private val getRegularPaymentsUseCase = GetRegularPayments(RegularPaymentsRepositoryImpl())

    private val savePaymentTypesUseCase = SavePaymentTypes(PaymentTypesRepositoryImpl())
    private val getPaymentTypesUseCase = GetPaymentTypes(PaymentTypesRepositoryImpl())
    private val delPaymentTypesUseCase = DelPaymentTypes(PaymentTypesRepositoryImpl())

    private val _regularPayments = MutableLiveData<RegularPayments>().apply {
        value = null
    }

    private val _paymentTypes = MutableLiveData<List<PaymentType>>().apply {
        value = listOf()
    }

    private val _selectedPaymentType = MutableLiveData<Int>().apply {
        value = null
    }

    val regularPayments: LiveData<RegularPayments> = _regularPayments
    val paymentTypes: LiveData<List<PaymentType>> = _paymentTypes
    val selectedPaymentType: LiveData<Int> = _selectedPaymentType

    fun getRegularPayments() {
        if (_regularPayments.value == null) {
            viewModelScope.launch {
                _regularPayments.value = getRegularPaymentsUseCase.execute()
            }
        }
    }

    fun setRegularPayments(data: RegularPayments) {
        viewModelScope.launch {
            _regularPayments.value = saveRegularPaymentsUseCase.execute(data)
        }
    }

    fun setSelectedPaymentsType(position: Int){
        _selectedPaymentType.value = position
    }

    fun getPaymentsTypes() {
        if (_paymentTypes.value?.isEmpty() == true) {
            viewModelScope.launch {
                _paymentTypes.value = getPaymentTypesUseCase.execute()
            }
        }
    }

    fun setPaymentsType(data: PaymentType) {
        val oldList = createMutableList()
        viewModelScope.launch {
            if (selectedPaymentType.value != null) {
                oldList[selectedPaymentType.value!!] = savePaymentTypesUseCase.execute(data)
            } else {
                oldList.add(savePaymentTypesUseCase.execute(data))
            }
            returnNewVal(oldList)
        }
    }

    fun delPaymentType(paymentTypePosition: Int, isDelFromCal: Boolean) {
        viewModelScope.launch {
            val oldList = createMutableList()
            val result = delPaymentTypesUseCase.delPaymentType(oldList[paymentTypePosition], isDelFromCal)
            if (result) {
                oldList.removeAt(paymentTypePosition)
                returnNewVal(oldList)
            }
        }
    }

    private fun createMutableList(): MutableList<PaymentType> {
        val oldList: MutableList<PaymentType> = mutableListOf()
        _paymentTypes.value?.let { oldList.addAll(it) }
        return oldList
    }

    private fun returnNewVal(oldList: MutableList<PaymentType>) {
        _paymentTypes.value = oldList.toList()
        _selectedPaymentType.value = null
    }
}