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
import com.example.myfinance.databinding.DialogAddPrepaidBinding
import com.example.myfinance.utils.Constants

class AddPrepaidDialog : DialogFragment() {

    var _binding: DialogAddPrepaidBinding? = null
    val binding get() = _binding!!

    var selectedDay = 1
    var selectedSum = 0.0

    companion object {

        const val TAG = "AddPrepaidDialog"
        const val PREPAID_RESULT_CODE = 2

        const val TAG_PREPAID_SUM = "tag_prepaid_sum"
        const val TAG_PREPAID_DAY = "tag_prepaid_day"


        const val KEY_SUM = "SUM"
        const val KEY_DAY = "DAY"

        fun newInstance(sum: Double, day: Int): DialogFragment {
            val args = Bundle()
            args.putDouble(KEY_SUM, sum)
            args.putInt(KEY_DAY, day)
            val fragment = DialogFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = DialogAddPrepaidBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonPositive: Button = binding.buttonPositive
        val buttonNegative: Button = binding.buttonNegative
        val paymentSum: EditText = binding.editTextSummary
        val paymentDay: Spinner = binding.spinnerDay

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            Constants.days
        )
        adapter.setDropDownViewResource(R.layout.simple_dropdown_item_1line)
        paymentDay.adapter = adapter

        arguments?.let {
            paymentSum.setText(it.getDouble(KEY_SUM).toString())
            paymentDay.setSelection(Constants.days.indexOf(it.getInt(KEY_DAY)))
        }

        paymentDay.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                selectedDay = Constants.days[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>){}
        }

        paymentSum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                selectedSum = s.toString().toDouble()
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
        }

        buttonNegative.setOnClickListener {
            dismiss()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val params = dialog!!.window!!.attributes
            params.width = LinearLayoutCompat.LayoutParams.MATCH_PARENT
            dialog!!.window!!.attributes = params as android.view.WindowManager.LayoutParams
        } else {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels
            val params = dialog!!.window!!.attributes
            params.height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            dialog!!.window!!.setLayout(dpWidth ,
                params.height)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}