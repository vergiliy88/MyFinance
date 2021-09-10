package com.example.myfinance.ui.type_payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

import com.example.myfinance.databinding.FragmentPaymentTypeBinding
import com.example.myfinance.ui.base.BaseFragment
import com.example.myfinance.ui.type_payment.dialogs.AddPrepaidDialog
import com.example.myfinance.ui.type_payment.dialogs.AddPrepaidDialog.Companion.PREPAID_RESULT_CODE
import com.example.myfinance.ui.type_payment.dialogs.AddSalaryDialog
import com.example.myfinance.ui.type_payment.dialogs.AddSalaryDialog.Companion.SALARY_RESULT_CODE

class PaymentTypeFragment: BaseFragment<FragmentPaymentTypeBinding, String>() {

    companion object {
        @JvmStatic
        fun newInstance(viewModal: PaymentTypeViewModel): PaymentTypeFragment {
            val fragment = PaymentTypeFragment()
            fragment._viewModal = viewModal
            return fragment
        }
    }

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
            args.putDouble(AddSalaryDialog.KEY_SUM, 234.00)
            args.putInt(AddSalaryDialog.KEY_DAY, 7)
            dialogSalary.arguments = args
            dialogSalary.setTargetFragment(this, SALARY_RESULT_CODE)
            dialogSalary.show(requireActivity().supportFragmentManager, AddSalaryDialog.TAG)
        }

        buttonPrepaid.setOnClickListener {
            val dialogSalary = AddPrepaidDialog()
            val args = Bundle()
            args.putDouble(AddPrepaidDialog.KEY_SUM, 234.00)
            args.putInt(AddPrepaidDialog.KEY_DAY, 2)
            dialogSalary.arguments = args
            dialogSalary.setTargetFragment(this, PREPAID_RESULT_CODE)
            dialogSalary.show(requireActivity().supportFragmentManager, AddPrepaidDialog.TAG)
        }

        buttonAddPaymentType.setOnClickListener {
            Log.d("TEST", "buttonAddPaymentType")
        }

        _viewModal.data.observe(viewLifecycleOwner, Observer {
            it?.let {

            }
        })


        return root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        when (resultCode) {
            SALARY_RESULT_CODE -> {
                Log.d("RESULT DIALOG 1 ", "" + data.getIntExtra(AddSalaryDialog.TAG_SALARY_DAY, 1))
                Log.d("RESULT DIALOG 2", "" + data.getDoubleExtra(AddSalaryDialog.TAG_SALARY_SUM, 0.0))
            }
            PREPAID_RESULT_CODE ->{
                Log.d("RESULT DIALOG 1 ", "" + data.getIntExtra(AddSalaryDialog.TAG_SALARY_DAY, 1))
                Log.d("RESULT DIALOG 2", "" + data.getDoubleExtra(AddSalaryDialog.TAG_SALARY_SUM, 0.0))
            }
        }
    }
}