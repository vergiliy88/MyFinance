package com.example.myfinance.ui.type_payment.dialogs


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.myfinance.databinding.DialogConfirmDelPaymentTypeBinding
import com.example.myfinance.ui.base.BaseDialog

class ConfirmDeletePaymentTypeDialog: BaseDialog<DialogConfirmDelPaymentTypeBinding>() {

    private var isDelFromCal = false
    private var paymentTypePosition = 0

    companion object {

        const val TAG = "ConfirmDeletePaymentTypeDialog"
        const val CONFIRM_DEL_PAYMENT_TYPE = 4
        const val TAG_PAYMENT_TYPE_POSITION = "tag_payment_type_position"
        const val TAG_IS_DEL_FROM_CAL = "tag_is_del_from_cal"

        fun newInstance(paymentTypeId: Int): ConfirmDeletePaymentTypeDialog {
            val args = Bundle()
            args.putInt(TAG_PAYMENT_TYPE_POSITION, paymentTypeId)
            val fragment = ConfirmDeletePaymentTypeDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paymentTypePosition = it.getInt(TAG_PAYMENT_TYPE_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = DialogConfirmDelPaymentTypeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonPositive: Button = binding.buttonPositive
        val buttonNegative: Button = binding.buttonNegative
        val checkBoxDelFromCal: CheckBox = binding.checkBoxDelFromCalendar

        checkBoxDelFromCal.setOnCheckedChangeListener{ view, isChecked  ->
            isDelFromCal = isChecked
        }

        buttonPositive.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TAG_PAYMENT_TYPE_POSITION, paymentTypePosition)
            intent.putExtra(TAG_IS_DEL_FROM_CAL, isDelFromCal)
            targetFragment?.onActivityResult (
                targetRequestCode,
                CONFIRM_DEL_PAYMENT_TYPE,
                intent)
            dismiss()
        }

        buttonNegative.setOnClickListener {
            dismiss()
        }

        return root
    }
}