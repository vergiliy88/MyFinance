package com.vergiliy.myfinance.ui.home.del_payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vergiliy.myfinance.R
import com.vergiliy.myfinance.databinding.FragmentViewEditPaymentBinding
import com.vergiliy.myfinance.domain.utils.Utils
import com.vergiliy.myfinance.ui.base.BaseFragment
import com.vergiliy.myfinance.ui.models.CalendarDate

class DelPaymentFragment : BaseFragment<FragmentViewEditPaymentBinding>() {
    private lateinit var _viewModal: DelPaymentViewModal
    private lateinit var adapter: DelPaymentAdapter
    private var isEdit: Boolean = false
    private lateinit var selectedDate: CalendarDate

    companion object {
        const val TAG_FRAGMENT = "DelPaymentFragment"
        private const val DATA_VALUE = "date_value"
        @JvmStatic
        fun newInstance(date: CalendarDate): DelPaymentFragment {
            val args = Bundle()
            args.putParcelable(DATA_VALUE, date)
            val fragment = DelPaymentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModal =
            ViewModelProvider(requireActivity()).get(DelPaymentViewModal::class.java)

        arguments?.let {
            selectedDate = it.getParcelable(DATA_VALUE) ?: CalendarDate()
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

        setToolBarTitle(resources.getString(R.string.button_del_payment), activity)

        val paymentTemplateList: RecyclerView = binding.paymentTemplateList
        val buttonSavePayment = binding.buttonSavePayment
        val dateValue = binding.valueDate
        val sumValue = binding.valueSum
        buttonSavePayment.text = resources.getText(R.string.button_del)

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

        adapter = DelPaymentAdapter()
        adapter.setCheckBoxListener(object: DelPaymentAdapter.SelectPayment{
            override fun onSelectPayment(position: Int, isSelected: Boolean) {
                _viewModal.onSelectForDelete(position, isSelected)
            }
        })
        paymentTemplateList.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
        return root
    }


}