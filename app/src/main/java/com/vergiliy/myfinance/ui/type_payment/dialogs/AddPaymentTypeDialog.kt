package com.vergiliy.myfinance.ui.type_payment.dialogs

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.vergiliy.myfinance.databinding.DialogAddPaymentTypeBinding
import com.vergiliy.myfinance.domain.models.PaymentType
import com.vergiliy.myfinance.ui.base.BaseDialog
import com.vergiliy.myfinance.utils.Constants
import com.vergiliy.myfinance.utils.Constants.Companion.DEFAULT_SUM

class AddPaymentTypeDialog : BaseDialog<DialogAddPaymentTypeBinding>() {


    var paymentType: PaymentType = PaymentType()

    companion object {

        const val TAG = "AddPaymentTypeDialog"
        const val ADD_TYPE_RESULT = 3

        const val TAG_PAYMENT_TYPE = "tag_payment_type"


        fun newInstance(paymentType: PaymentType): AddPaymentTypeDialog {
            val args = Bundle()
            args.putParcelable(TAG_PAYMENT_TYPE, paymentType)
            val fragment = AddPaymentTypeDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DialogAddPaymentTypeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonPositive: Button = binding.buttonPositive
        val buttonNegative: Button = binding.buttonNegative
        val paymentName: EditText = binding.inputName
        val paymentColor: Spinner = binding.spinnerColor
        val paymentSum: EditText = binding.inputSum

        val adapter = ColorArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            Constants.COLORS
        )
        adapter.setDropDownViewResource(R.layout.simple_dropdown_item_1line)
        paymentColor.adapter = adapter

        arguments?.let {
            val paymentTypeParcel = it.getParcelable<PaymentType>(TAG_PAYMENT_TYPE) ?: PaymentType()
            paymentType.color = paymentTypeParcel.color ?: ""
            paymentType.sum = paymentTypeParcel.sum ?: DEFAULT_SUM
            paymentType.name = paymentTypeParcel.name ?: ""
            paymentType.id = paymentTypeParcel.id
            paymentSum.setText(paymentType.sum.toString())
            paymentColor.setSelection(Constants.COLORS.indexOf(paymentType.color))
            paymentName.setText(paymentType.name)
        }

        paymentColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                view?.let {
                    paymentType.color = Constants.COLORS[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        paymentSum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { string ->
                    paymentType.sum = if (string.isNotEmpty()) {
                        s.toString().toDouble()
                    } else {
                        DEFAULT_SUM
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        paymentName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { string ->
                    paymentType.name = string.toString()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        buttonPositive.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TAG_PAYMENT_TYPE, paymentType)
            targetFragment?.onActivityResult(
                targetRequestCode,
                ADD_TYPE_RESULT,
                intent
            )
            dismiss()
        }

        buttonNegative.setOnClickListener {
            dismiss()
        }

        return root
    }

}