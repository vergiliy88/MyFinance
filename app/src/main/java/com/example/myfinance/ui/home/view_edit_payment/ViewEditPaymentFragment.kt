package com.example.myfinance.ui.home.view_edit_payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.R
import com.example.myfinance.databinding.FragmentViewEditPaymentBinding
import com.example.myfinance.domain.utils.Utils
import com.example.myfinance.ui.base.BaseFragment
import com.example.myfinance.ui.models.StatisticDate

open class ViewEditPaymentFragment: BaseFragment<FragmentViewEditPaymentBinding>(),
     ViewEditPaymentAdapter.SetCommentAndSumValue {
    private lateinit var _viewModal: ViewEditPaymentViewModel
    private lateinit var adapter: ViewEditPaymentAdapter
    private var isEdit: Boolean = false
    private lateinit var selectedDate: StatisticDate

    companion object {
        const val TAG_FRAGMENT = "ViewEditPaymentFragment"
        private const val DATA_VALUE = "date_value"
        private const val IS_EDITABLE_VALUE = "is_editable_value"
        @JvmStatic
        fun newInstance(date: StatisticDate, isEdit: Boolean): ViewEditPaymentFragment {
            val args = Bundle()
            args.putParcelable(DATA_VALUE, date)
            args.putBoolean(IS_EDITABLE_VALUE, isEdit)
            val fragment = ViewEditPaymentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModal =
            ViewModelProvider(requireActivity()).get(ViewEditPaymentViewModel::class.java)

        arguments?.let {
            selectedDate = it.getParcelable(DATA_VALUE) ?: StatisticDate()
            isEdit = it.getBoolean(IS_EDITABLE_VALUE)
        }
        if (savedInstanceState == null) {
            selectedDate?.let {
                _viewModal.getData(it.day!!,
                    it.month!!,
                    it.year!!)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentViewEditPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val paymentTemplateList: RecyclerView = binding.paymentTemplateList
        val buttonSavePayment = binding.buttonSavePayment
        val dateValue = binding.valueDate
        val sumValue = binding.valueSum
        val sumLabel = binding.labelSum

        buttonSavePayment.visibility = GONE
        sumValue.visibility = VISIBLE
        sumLabel.visibility = VISIBLE

        setToolBarTitle(resources.getString(R.string.button_view_payment), activity)

        if (this.isEdit) {
            buttonSavePayment.visibility = VISIBLE
            sumValue.visibility = GONE
            sumLabel.visibility = GONE
            setToolBarTitle(resources.getString(R.string.button_edit_payment), activity)
        }

        selectedDate?.let { calendare ->
            dateValue.text = "${calendare.day}." +
                    "${Utils.convertMonthFromCal(calendare.month!!)}." +
                    "${calendare.year}"
        }


        buttonSavePayment.setOnClickListener {
            _viewModal.updatePayments()
            parentFragmentManager.popBackStack()
        }

        _viewModal.payments.observe(viewLifecycleOwner, {
            it?.let {
                adapter.clear()
                adapter.populate(it)
                if (!this.isEdit) {
                    var sum = 0.0
                    for (item in it) {
                        sum += item.realSum ?: 0.0
                    }
                    sumValue.text = sum.toString()
                }
            }
        })

        adapter = ViewEditPaymentAdapter()
        adapter.setCheckBoxListener(this)
        val isEditable = this.isEdit ?: false
        adapter.setIsEditable(isEditable)
        paymentTemplateList.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }

        return root
    }

    override fun onSetComment(position: Int, value: String) {
        _viewModal.setCommentPaymentTemplate(position, value)
    }

    override fun onSetSum(position: Int, value: Double) {
        _viewModal.setSumPaymentTemplate(position, value)
    }

}