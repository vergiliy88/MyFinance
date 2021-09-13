package com.example.myfinance.ui.type_payment.dialogs

import android.R
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.DialogFragment
import com.example.myfinance.databinding.DialogAddPaymentTypeBinding
import com.example.myfinance.utils.Constants

class AddPaymentTypeDialog : DialogFragment() {

    var _binding: DialogAddPaymentTypeBinding? = null
    val binding get() = _binding!!

    var selectedColor = ""
    var selectedSum = 0.0
    var selectedName = ""

    companion object {

        const val TAG = "AddPaymentTypeDialog"
        const val ADD_TYPE_RESULT = 3

        const val TAG_PAYMENT_SUM = "tag_payment_sum"
        const val TAG_PAYMENT_COLOR = "tag_payment_color"
        const val TAG_PAYMENT_NAME = "tag_payment_name"


        const val KEY_SUM = "SUM"
        const val KEY_COLOR = "COLOR"
        const val KEY_NAME = "NAME"

        fun newInstance(sum: Double, color: String, name: String): AddPaymentTypeDialog {
            val args = Bundle()
            args.putDouble(KEY_SUM, sum)
            args.putString(KEY_COLOR, color)
            args.putString(KEY_NAME, name)
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
            Constants.colors
        )
        adapter.setDropDownViewResource(R.layout.simple_dropdown_item_1line)
        paymentColor.adapter = adapter

        arguments?.let {
            selectedColor = it.getString(KEY_COLOR) ?: ""
            selectedSum = it.getDouble(KEY_SUM) ?: 0.0
            selectedName = it.getString(KEY_NAME) ?: ""
            paymentSum.setText(it.getDouble(KEY_SUM, 0.0).toString())
            paymentColor.setSelection(Constants.colors.indexOf(it.getString(KEY_COLOR, Constants.colors[0])))
            paymentName.setText(it.getString(KEY_SUM, ""))
        }

        paymentColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                view?.let {
                    selectedColor = Constants.colors[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        paymentSum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { string ->
                    selectedSum = if (string.isNotEmpty()) {
                        s.toString().toDouble()
                    } else {
                        0.0
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        paymentName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { string ->
                    selectedName = string.toString()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        buttonPositive.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TAG_PAYMENT_NAME, selectedSum)
            intent.putExtra(TAG_PAYMENT_COLOR, selectedColor)
            intent.putExtra(TAG_PAYMENT_SUM, selectedSum)
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

    override fun onResume() {
        super.onResume()
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val params = dialog!!.window!!.attributes
            params.width = LinearLayoutCompat.LayoutParams.MATCH_PARENT
            dialog!!.window!!.attributes = params as android.view.WindowManager.LayoutParams
        } else {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels
            val params = dialog!!.window!!.attributes
            params.height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            dialog!!.window!!.setLayout(
                dpWidth,
                params.height
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}