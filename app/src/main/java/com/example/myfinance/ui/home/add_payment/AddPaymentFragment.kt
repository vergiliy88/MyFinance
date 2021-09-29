package com.example.myfinance.ui.home.add_payment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.R
import com.example.myfinance.databinding.FragmentAddPaymentBinding
import com.example.myfinance.domain.utils.Utils
import com.example.myfinance.ui.base.BaseFragment
import com.example.myfinance.ui.models.StatisticDate
import java.util.*

open class AddPaymentFragment: BaseFragment<FragmentAddPaymentBinding>(),
    AddPaymentAdapter.SetSelectedPaymentType, AddPaymentAdapter.SetCommentAndSumValue {
    private lateinit var _viewModal: AddPaymentViewModel
    private lateinit var adapter: AddPaymentAdapter

    private var selectedDate: StatisticDate? = null


    companion object {
        const val TAG_FRAGMENT = "AddPaymentFragment"
        private const val DATA_VALUE = "date_value"
        @JvmStatic
        fun newInstance(date: StatisticDate?): AddPaymentFragment {
            val args = Bundle()
            args.putParcelable(DATA_VALUE, date)
            val fragment = AddPaymentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModal =
            ViewModelProvider(requireActivity()).get(AddPaymentViewModel::class.java)

        arguments?.let {
            selectedDate = it.getParcelable(DATA_VALUE)
        }

        if (this.selectedDate == null) {
            val calendar: Calendar = Calendar.getInstance();
            _viewModal.setDateFrom(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
            _viewModal.setDateTo(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
        } else {
            _viewModal.setDateFrom(this.selectedDate!!.year ?: 0,
                                this.selectedDate!!.month ?: 0,
                                  this.selectedDate!!.day ?: 0)
            _viewModal.setDateTo(this.selectedDate!!.year ?: 0,
                                this.selectedDate!!.month ?: 0,
                                    this.selectedDate!!.day ?: 0)
        }
        _viewModal.generateList()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentAddPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val buttonDateFrom = binding.buttonDateFrom
        val buttonDateTo = binding.buttonDateTo
        val paymentTemplateList: RecyclerView = binding.paymentTemplateList
        val buttonSavePayment = binding.buttonSavePayment

        setToolBarTitle(resources.getString(R.string.button_add_payment), activity)

        buttonDateFrom.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                _viewModal.setDateFrom(year, monthOfYear, dayOfMonth)
            }, _viewModal.dateFrom.value!!.year!!, _viewModal.dateFrom.value!!.month!!, _viewModal.dateFrom.value!!.day!!)
            dpd.show()
        }

        buttonDateTo.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                _viewModal.setDateTo(year, monthOfYear, dayOfMonth)
            }, _viewModal.dateTo.value!!.year!!, _viewModal.dateTo.value!!.month!!, _viewModal.dateTo.value!!.day!!)
            dpd.show()
        }

        buttonSavePayment.setOnClickListener {
            _viewModal.savePayments()
            parentFragmentManager.popBackStack()
        }

        _viewModal.dateFrom.observe(viewLifecycleOwner, {
            it?.let {dateFrom ->
                buttonDateFrom.text = "${dateFrom.day!!}." +
                        "${Utils.convertMonthFromCal(dateFrom.month!!)}." +
                        "${dateFrom.year!!}"
            }
        })

        _viewModal.dateTo.observe(viewLifecycleOwner, {
            it?.let {dateTo->
                buttonDateTo.text = "${dateTo.day!!}." +
                        "${Utils.convertMonthFromCal(dateTo.month!!)}." +
                        "${dateTo.year!!}"
            }
        })

        _viewModal.paymentTypes.observe(viewLifecycleOwner, {
            it?.let {
                val items = _viewModal.paymentTemplate
                val productDiffUtilCallback =
                    AddPaymentTemplateDiffUtilCallback(adapter.data, items)
                val productDiffResult = DiffUtil.calculateDiff(productDiffUtilCallback)

                adapter.clear()
                adapter.populate(items)
                productDiffResult.dispatchUpdatesTo(adapter)
            }
        })

        adapter = AddPaymentAdapter()
        adapter.setCheckBoxListener(this, this)
        paymentTemplateList.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }

        return root
    }

    override fun onSelectPaymentType(position: Int) {
        _viewModal.setPaymentTemplateSelected(position)
    }

    override fun onSetComment(position: Int, value: String) {
        _viewModal.setCommentPaymentTemplate(position, value)
    }

    override fun onSetSum(position: Int, value: Double) {
        _viewModal.setSumPaymentTemplate(position, value)
    }
}