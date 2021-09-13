package com.example.myfinance.ui.type_payment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.repositories.RegularPaymentsRepositoryImpl
import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.domain.usecase.GetRegularPayments
import com.example.myfinance.domain.usecase.SaveRegularPayments
import com.example.myfinance.ui.base.BaseViewModal
import kotlinx.coroutines.launch


class PaymentTypeViewModel: BaseViewModal() {

    private val saveRegularPaymentsUseCase = SaveRegularPayments(RegularPaymentsRepositoryImpl())
    private val getRegularPaymentsUseCase = GetRegularPayments(RegularPaymentsRepositoryImpl())

    private val _regularPayments = MutableLiveData<RegularPayments>().apply {
        value = null
    }

    val regularPayments: LiveData<RegularPayments> = _regularPayments

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
}