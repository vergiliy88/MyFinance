package com.example.myfinance.ui.type_payment.dialogs

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.myfinance.databinding.DialogAddPrepaidBinding
import com.example.myfinance.ui.base.BaseDialog
import com.example.myfinance.utils.Constants
import com.example.myfinance.utils.Constants.Companion.DEFAULT_DAY
import com.example.myfinance.utils.Constants.Companion.DEFAULT_SUM

class AddPrepaidDialog : BaseDialog<DialogAddPrepaidBinding>() {

    var selectedDay = DEFAULT_DAY
    var selectedSum = DEFAULT_SUM

    companion object {

        const val TAG = "AddPrepaidDialog"
        const val PREPAID_RESULT_CODE = 2

        const val TAG_PREPAID_SUM = "tag_prepaid_sum"
        const val TAG_PREPAID_DAY = "tag_prepaid_day"


        const val KEY_SUM = "SUM"
        const val KEY_DAY = "DAY"

        fun newInstance(sum: Double, day: Int): AddPrepaidDialog {
            val args = Bundle()
            args.putDouble(KEY_SUM, sum)
            args.putInt(KEY_DAY, day)
            val fragment = AddPrepaidDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = DialogAddPrepaidBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonPositive: Button = binding.buttonPositive
        val buttonNegative: Button = binding.buttonNegative
        val paymentSum: EditText = binding.editTextSummary
        val paymentDay: Spinner = binding.spinnerDay

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            Constants.DAYS
        )
        adapter.setDropDownViewResource(R.layout.simple_dropdown_item_1line)
        paymentDay.adapter = adapter

        arguments?.let {
            selectedDay = Constants.DAYS.indexOf(it.getInt(KEY_DAY))
            selectedSum = it.getDouble(KEY_SUM)
            paymentSum.setText(it.getDouble(KEY_SUM).toString())
            paymentDay.setSelection(Constants.DAYS.indexOf(it.getInt(KEY_DAY)))
        }

        paymentDay.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long){
                view?.let{
                    selectedDay = Constants.DAYS[position]
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>){}
        }

        paymentSum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { string ->
                    selectedSum = if (string.isNotEmpty()){
                        s.toString().toDouble()
                    } else {
                        DEFAULT_SUM
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        buttonPositive.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TAG_PREPAID_SUM, selectedSum)
            intent.putExtra(TAG_PREPAID_DAY, selectedDay)
            targetFragment?.onActivityResult (
                targetRequestCode,
                PREPAID_RESULT_CODE,
                intent)
            dismiss()
        }

        buttonNegative.setOnClickListener {
            dismiss()
        }

        return root
    }

}