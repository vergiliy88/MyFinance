package com.example.myfinance.ui.type_payment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.R
import com.example.myfinance.databinding.FragmentPaymentTypeBinding
import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.ui.base.BaseAdapter
import com.example.myfinance.ui.base.BaseFragment
import com.example.myfinance.ui.type_payment.dialogs.AddPaymentTypeDialog
import com.example.myfinance.ui.type_payment.dialogs.AddPaymentTypeDialog.Companion.ADD_TYPE_RESULT
import com.example.myfinance.ui.type_payment.dialogs.AddPaymentTypeDialog.Companion.TAG_PAYMENT_TYPE
import com.example.myfinance.ui.type_payment.dialogs.AddPrepaidDialog
import com.example.myfinance.ui.type_payment.dialogs.AddPrepaidDialog.Companion.PREPAID_RESULT_CODE
import com.example.myfinance.ui.type_payment.dialogs.AddSalaryDialog
import com.example.myfinance.ui.type_payment.dialogs.AddSalaryDialog.Companion.SALARY_RESULT_CODE
import com.example.myfinance.ui.type_payment.dialogs.ConfirmDeletePaymentTypeDialog
import com.example.myfinance.ui.type_payment.dialogs.ConfirmDeletePaymentTypeDialog.Companion.CONFIRM_DEL_PAYMENT_TYPE
import com.example.myfinance.ui.type_payment.dialogs.ConfirmDeletePaymentTypeDialog.Companion.TAG_IS_DEL_FROM_CAL
import com.example.myfinance.ui.type_payment.dialogs.ConfirmDeletePaymentTypeDialog.Companion.TAG_PAYMENT_TYPE_POSITION
import com.example.myfinance.utils.Constants.Companion.DEFAULT_DAY
import com.example.myfinance.utils.Constants.Companion.DEFAULT_SUM

class PaymentTypeFragment: BaseFragment<FragmentPaymentTypeBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): PaymentTypeFragment {
            return PaymentTypeFragment()
        }
    }

    private lateinit var _viewModal: PaymentTypeViewModel
    private lateinit var adapter: PaymentTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModal =
            ViewModelProvider(requireActivity()).get(PaymentTypeViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentPaymentTypeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val paymentTypeList: RecyclerView = binding.paymentTypeList
        val buttonSalary: Button = binding.buttonSalary
        val buttonPrepaid: Button = binding.buttonPrepaid
        val buttonAddPaymentType: Button = binding.buttonAddPaymentType

        val listeners = mapOf(
            R.id.button_edit to BaseAdapter.OnViewClickListener { _, value ->
                val list = _viewModal.paymentTypes.value
                val position = value as Int
                _viewModal.setSelectedPaymentsType(position)
                list?.get(position)?.let {
                    val dialog = AddPaymentTypeDialog.newInstance(it)
                    dialog.setTargetFragment(this, ADD_TYPE_RESULT)
                    dialog.show(parentFragmentManager, AddPaymentTypeDialog.TAG)
                }
            },
            R.id.button_remove to BaseAdapter.OnViewClickListener { _, value ->
                val list = _viewModal.paymentTypes.value
                val position = value as Int
                _viewModal.setSelectedPaymentsType(position)
                list?.get(position)?.let {
                    val dialog = ConfirmDeletePaymentTypeDialog.newInstance(position)
                    dialog.setTargetFragment(this, CONFIRM_DEL_PAYMENT_TYPE)
                    dialog.show(parentFragmentManager, ConfirmDeletePaymentTypeDialog.TAG)
                }
            }
        )

       adapter = PaymentTypeAdapter(listeners)
        paymentTypeList.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }

        buttonSalary.setOnClickListener {
            val dialogSalary = AddSalaryDialog()
            val args = Bundle()
            args.putDouble(AddSalaryDialog.KEY_SUM, _viewModal.regularPayments.value?.salary ?: DEFAULT_SUM)
            args.putInt(AddSalaryDialog.KEY_DAY, _viewModal.regularPayments.value?.salaryDay ?: DEFAULT_DAY)
            dialogSalary.arguments = args
            dialogSalary.setTargetFragment(this, SALARY_RESULT_CODE)
            dialogSalary.show(parentFragmentManager, AddSalaryDialog.TAG)
        }

        buttonPrepaid.setOnClickListener {
            val dialogSalary = AddPrepaidDialog()
            val args = Bundle()
            args.putDouble(AddPrepaidDialog.KEY_SUM, _viewModal.regularPayments.value?.prepaid ?: DEFAULT_SUM)
            args.putInt(AddPrepaidDialog.KEY_DAY, _viewModal.regularPayments.value?.prepaidDay ?: DEFAULT_DAY)
            dialogSalary.arguments = args
            dialogSalary.setTargetFragment(this, PREPAID_RESULT_CODE)
            dialogSalary.show(parentFragmentManager, AddPrepaidDialog.TAG)
        }

        buttonAddPaymentType.setOnClickListener {
            val dialog = AddPaymentTypeDialog.newInstance(PaymentType())
            dialog.setTargetFragment(this, ADD_TYPE_RESULT)
            dialog.show(parentFragmentManager, AddPaymentTypeDialog.TAG)
        }

        _viewModal.regularPayments.observe(viewLifecycleOwner, Observer {
            it?.let {
                val salary = it.salary ?: DEFAULT_SUM
                val salaryDay = it.salaryDay ?: DEFAULT_DAY
                buttonSalary.text = "$salaryDay : ${resources.getString(R.string.number)}" +
                        " - $salary  ${resources.getString(R.string.currency)}"

                val prepaid = it.prepaid ?: DEFAULT_SUM
                val prepaidDay = it.prepaidDay ?: DEFAULT_DAY

                buttonPrepaid.text = "$prepaidDay : ${resources.getString(R.string.number)}" +
                        " - $prepaid  ${resources.getString(R.string.currency)}"
            }
        })

        _viewModal.paymentTypes.observe(viewLifecycleOwner, {
            it?.let {
                adapter.clear()
                adapter.populate(it)
            }
        })

        _viewModal.getRegularPayments()
        _viewModal.getPaymentsTypes()

        return root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        when (resultCode) {
            SALARY_RESULT_CODE -> {
                val salaryDay = data.getIntExtra(AddSalaryDialog.TAG_SALARY_DAY, DEFAULT_DAY)
                val salarySum = data.getDoubleExtra(AddSalaryDialog.TAG_SALARY_SUM, DEFAULT_SUM)
                val regularPayments = _viewModal.regularPayments.value ?: RegularPayments()
                regularPayments.salaryDay = salaryDay
                regularPayments.salary = salarySum
                _viewModal.setRegularPayments(regularPayments)
            }

            PREPAID_RESULT_CODE ->{
                val prepaidDay = data.getIntExtra(AddPrepaidDialog.TAG_PREPAID_DAY, DEFAULT_DAY)
                val prepaidSum = data.getDoubleExtra(AddPrepaidDialog.TAG_PREPAID_SUM, DEFAULT_SUM)
                val regularPayments = _viewModal.regularPayments.value ?: RegularPayments()
                regularPayments.prepaidDay = prepaidDay
                regularPayments.prepaid = prepaidSum
                _viewModal.setRegularPayments(regularPayments)
            }

            ADD_TYPE_RESULT -> {
                val paymentType = data.getParcelableExtra<PaymentType>(TAG_PAYMENT_TYPE)
                paymentType?.let {
                    _viewModal.setPaymentsType(it)
                }
            }

            CONFIRM_DEL_PAYMENT_TYPE -> {
                val isDelFromCal = data.getBooleanExtra(TAG_IS_DEL_FROM_CAL, false)
                val paymentTypePosition = data.getIntExtra(TAG_PAYMENT_TYPE_POSITION, 0)
                _viewModal.delPaymentType(paymentTypePosition, isDelFromCal)
            }
        }
    }
}