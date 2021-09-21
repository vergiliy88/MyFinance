package com.example.myfinance.ui.type_payment


import androidx.lifecycle.*
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
    val regularPayments: LiveData<RegularPayments> = _regularPayments
    var paymentTypes: LiveData<List<PaymentType>> = getPaymentTypesUseCase.getAllFlow().asLiveData()

    fun getRegularPayments() {
        if (_regularPayments.value == null) {
            viewModelScope.launch {
                _regularPayments.value = getRegularPaymentsUseCase.getAll()
            }
        }
    }
    fun setRegularPayments(data: RegularPayments) {
        viewModelScope.launch {
            _regularPayments.value = saveRegularPaymentsUseCase.execute(data)
        }
    }

    fun setPaymentsType(data: PaymentType) {
        viewModelScope.launch {
            savePaymentTypesUseCase.execute(data)
        }
    }
    fun delPaymentType(paymentTypePosition: Int, isDelFromCal: Boolean) {
        viewModelScope.launch {
            val oldList = createMutableList()
            delPaymentTypesUseCase.delPaymentType(oldList[paymentTypePosition], isDelFromCal)
        }
    }

    private fun createMutableList(): MutableList<PaymentType> {
        val oldList: MutableList<PaymentType> = mutableListOf()
        paymentTypes.value?.let { oldList.addAll(it) }
        return oldList
    }
}