package com.example.myfinance.ui.type_payment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.R
import com.example.myfinance.databinding.FragmentPaymentTypeBinding
import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.ui.base.BaseFragment
import com.example.myfinance.ui.type_payment.dialogs.AddPaymentTypeDialog
import com.example.myfinance.ui.type_payment.dialogs.AddPaymentTypeDialog.Companion.ADD_TYPE_RESULT
import com.example.myfinance.ui.type_payment.dialogs.AddPrepaidDialog
import com.example.myfinance.ui.type_payment.dialogs.AddPrepaidDialog.Companion.PREPAID_RESULT_CODE
import com.example.myfinance.ui.type_payment.dialogs.AddSalaryDialog
import com.example.myfinance.ui.type_payment.dialogs.AddSalaryDialog.Companion.SALARY_RESULT_CODE

class PaymentTypeFragment: BaseFragment<FragmentPaymentTypeBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): PaymentTypeFragment {
            return PaymentTypeFragment()
        }
    }

    private lateinit var _viewModal: PaymentTypeViewModel

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

        buttonSalary.setOnClickListener {
            val dialogSalary = AddSalaryDialog()
            val args = Bundle()
            args.putDouble(AddSalaryDialog.KEY_SUM, _viewModal.regularPayments.value?.salary ?: 0.0)
            args.putInt(AddSalaryDialog.KEY_DAY, _viewModal.regularPayments.value?.salaryDay ?: 1)
            dialogSalary.arguments = args
            dialogSalary.setTargetFragment(this, SALARY_RESULT_CODE)
            dialogSalary.show(parentFragmentManager, AddSalaryDialog.TAG)
        }

        buttonPrepaid.setOnClickListener {
            val dialogSalary = AddPrepaidDialog()
            val args = Bundle()
            args.putDouble(AddPrepaidDialog.KEY_SUM, _viewModal.regularPayments.value?.prepaid ?: 0.0)
            args.putInt(AddPrepaidDialog.KEY_DAY, _viewModal.regularPayments.value?.prepaidDay ?: 1)
            dialogSalary.arguments = args
            dialogSalary.setTargetFragment(this, PREPAID_RESULT_CODE)
            dialogSalary.show(parentFragmentManager, AddPrepaidDialog.TAG)
        }

        buttonAddPaymentType.setOnClickListener {
            val dialog = AddPaymentTypeDialog.newInstance(0.0, "#0980e8", "test")
            dialog.setTargetFragment(this, ADD_TYPE_RESULT)
            dialog.show(parentFragmentManager, AddPaymentTypeDialog.TAG)
        }

        _viewModal.regularPayments.observe(viewLifecycleOwner, Observer {
            it?.let {
                val salary = it.salary ?: 0.0
                val salaryDay = it.salaryDay ?: 1
                buttonSalary.text = "$salaryDay : ${resources.getString(R.string.number)}" +
                        " - $salary  ${resources.getString(R.string.currency)}"

                val prepaid = it.prepaid ?: 0.0
                val prepaidDay = it.prepaidDay ?: 1

                buttonPrepaid.text = "$prepaidDay : ${resources.getString(R.string.number)}" +
                        " - $prepaid  ${resources.getString(R.string.currency)}"
            }
        })

        _viewModal.getRegularPayments()

        return root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        when (resultCode) {
            SALARY_RESULT_CODE -> {
                val salaryDay = data.getIntExtra(AddSalaryDialog.TAG_SALARY_DAY, 1)
                val salarySum = data.getDoubleExtra(AddSalaryDialog.TAG_SALARY_SUM, 0.0)
                val regularPayments = _viewModal.regularPayments.value ?: RegularPayments()
                regularPayments.salaryDay = salaryDay
                regularPayments.salary = salarySum
                _viewModal.setRegularPayments(regularPayments)
            }

            PREPAID_RESULT_CODE ->{
                val prepaidDay = data.getIntExtra(AddPrepaidDialog.TAG_PREPAID_DAY, 1)
                val prepaidSum = data.getDoubleExtra(AddPrepaidDialog.TAG_PREPAID_SUM, 0.0)
                val regularPayments = _viewModal.regularPayments.value ?: RegularPayments()
                regularPayments.prepaidDay = prepaidDay
                regularPayments.prepaid = prepaidSum
                _viewModal.setRegularPayments(regularPayments)
            }
        }
    }
}