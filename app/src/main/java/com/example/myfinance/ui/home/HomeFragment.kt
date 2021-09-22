package com.example.myfinance.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.CalendarView
import com.example.myfinance.databinding.FragmentHomeBinding
import java.util.*
import com.applandeo.materialcalendarview.EventDay
import com.example.myfinance.R
import com.example.myfinance.domain.models.Payment
import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.ui.base.BaseFragment
import com.example.myfinance.ui.home.add_edit_payment.AddPaymentFragment
import com.example.myfinance.utils.Constants.Companion.defaultColor
import com.example.myfinance.utils.UiUtils
import java.text.SimpleDateFormat


class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private lateinit var valueTotalPayments: TextView
    private lateinit var valueTotal: TextView
    private lateinit var valueTotalSalary: TextView
    private lateinit var calendarView: CalendarView

    private lateinit var _viewModal: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModal =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        calendarView = binding.calendarView
        val buttonAddPayment = binding.buttonAddPayment
        valueTotalPayments = binding.valueTotalPayments
        valueTotal = binding.valueTotal
        valueTotalSalary = binding.valueTotalSalary

        buttonAddPayment.setOnClickListener {
            val fragment = AddPaymentFragment.newInstance()
            UiUtils.replaceFragment(parentFragmentManager, fragment, AddPaymentFragment.TAG_FRAGMENT)
        }

        calendarView.setHeaderColor(R.color.purple_200)
        calendarView.currentPageDate
        calendarView.setOnPreviousPageChangeListener {
            _viewModal.setMonth(calendarView.currentPageDate.get(Calendar.MONTH))
            _viewModal.setYear(calendarView.currentPageDate.get(Calendar.YEAR))
        }

        calendarView.setOnForwardPageChangeListener {
            _viewModal.setMonth(calendarView.currentPageDate.get(Calendar.MONTH))
            _viewModal.setYear(calendarView.currentPageDate.get(Calendar.YEAR))
        }

        _viewModal.payments.observe(viewLifecycleOwner,{
            it?.let { list ->
                calcSums(list,
                _viewModal.regularPayments.value ?: RegularPayments(),
                _viewModal.paymentTypes.value ?: listOf())
            }
        })

        _viewModal.regularPayments.observe(viewLifecycleOwner, {
            it?.let { list ->
                calcSums(_viewModal.payments.value ?: listOf(),
                    list,
                    _viewModal.paymentTypes.value!!)
            }
        })

        _viewModal.paymentTypes.observe(viewLifecycleOwner, {
            it?.let { list ->
                calcSums(_viewModal.payments.value!!,
                    _viewModal.regularPayments.value ?: RegularPayments(),
                    list)
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calcSums(payments: List<Payment>,
                         regularPayments: RegularPayments,
                         paymentsType: List<PaymentType>) {

        val colorsList: MutableList<String> = mutableListOf()
        val events: MutableList<EventDay> = ArrayList()
        val eventsByDay: MutableMap<String, MutableList<String>> = mutableMapOf()
        var sumPayments = 0.0
        var sumSalary = 0.0

        for (item in payments){
            sumPayments += item.realSum!!
            if (paymentsType.isNotEmpty()) {
                for (paymentsTypeItem in paymentsType) {
                    if (paymentsTypeItem.id!! == item.paymentType!!) {
                        val color = paymentsTypeItem.color ?: defaultColor
                        if (eventsByDay.containsKey(item.date)) {
                            eventsByDay[item.date!!]?.add(color)
                        } else {
                            eventsByDay[item.date!!] = mutableListOf(color)
                        }
                    }
                }
            }
        }

        for (item in eventsByDay) {
            val calendar = Calendar.getInstance()
            for (color in item.value) {
                colorsList.add(color)
            }
            val drawable = CustomDrawable(colorsList)
            val sdf = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
            calendar.time = sdf.parse(item.key)
            events.add(EventDay(calendar, drawable))
        }

        calendarView.setEvents(events)

        regularPayments.salary?.let {
            sumSalary += it
        }
        regularPayments.prepaid?.let {
            sumSalary += it
        }
        valueTotalSalary.text = sumSalary.toString()
        valueTotalPayments.text = sumPayments.toString()
        valueTotal.text = (sumPayments + sumSalary).toString()
    }
}