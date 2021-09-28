package com.example.myfinance.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
import com.example.myfinance.ui.home.add_payment.AddPaymentFragment
import com.example.myfinance.utils.Constants.Companion.defaultColor
import com.example.myfinance.utils.UiUtils
import com.example.myfinance.ui.home.dialogs.SelectEventDialog
import com.example.myfinance.ui.home.view_edit_payment.ViewEditPaymentFragment
import com.example.myfinance.ui.models.StatisticDate


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
//        _viewModal.subscribeOnPayments()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        calendarView = binding.calendarView
        val buttonAddPayment = binding.buttonAddPayment
        valueTotalPayments = binding.valueTotalPayments
        valueTotal = binding.valueTotal
        valueTotalSalary = binding.valueTotalSalary
        val viewLoading = binding.loading

        buttonAddPayment.setOnClickListener {
            val fragment = AddPaymentFragment.newInstance(null)
            UiUtils.replaceFragment(parentFragmentManager, fragment, AddPaymentFragment.TAG_FRAGMENT)
        }

        val fm = parentFragmentManager

        calendarView.setOnDayClickListener { eventDay ->
            val listener = (object: SelectEventDialog.SelectTypeEvent {
                override fun onSelectTypeEvent(type: Int, date: StatisticDate) {
                    when(type){
                        SelectEventDialog.Companion.EventType.ADD -> {
                            val fragment = AddPaymentFragment.newInstance(date)
                            UiUtils.replaceFragment(fm,
                                fragment,
                                AddPaymentFragment.TAG_FRAGMENT)
                        }
                        SelectEventDialog.Companion.EventType.VIEW -> {
                            val fragment = ViewEditPaymentFragment.newInstance(date, false)
                            UiUtils.replaceFragment(fm,
                                fragment,
                                ViewEditPaymentFragment.TAG_FRAGMENT)
                        }
                        SelectEventDialog.Companion.EventType.EDIT -> {
                            val fragment = ViewEditPaymentFragment.newInstance(date, true)
                            UiUtils.replaceFragment(fm,
                                fragment,
                                ViewEditPaymentFragment.TAG_FRAGMENT)
                        }
                    }
                }
            })
            val date  = StatisticDate()
            date.year = eventDay.calendar.get(Calendar.YEAR)
            date.month = eventDay.calendar.get(Calendar.MONTH)
            date.day = eventDay.calendar.get(Calendar.DAY_OF_MONTH)
            val dialog = SelectEventDialog.Builder().setDate(date).setListener(listener).build()
            dialog.show(parentFragmentManager, SelectEventDialog.TAG)
        }

        calendarView.setHeaderColor(R.color.purple_200)

        calendarView.setOnPreviousPageChangeListener {
            _viewModal.setDate(calendarView.currentPageDate.get(Calendar.MONTH),
                calendarView.currentPageDate.get(Calendar.YEAR))
        }

        calendarView.setOnForwardPageChangeListener {
            _viewModal.setDate(calendarView.currentPageDate.get(Calendar.MONTH),
                calendarView.currentPageDate.get(Calendar.YEAR))
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
                    _viewModal.paymentTypes.value ?: listOf())
            }
        })

        _viewModal.paymentTypes.observe(viewLifecycleOwner, {
            it?.let { list ->
                calcSums(_viewModal.payments.value ?: listOf(),
                    _viewModal.regularPayments.value ?: RegularPayments(),
                    list)
            }
        })

        _viewModal.isShowLoading.observe(viewLifecycleOwner, {
            it?.let{ view ->
                if (view) {
                    viewLoading.progressBar.visibility = VISIBLE
                } else {
                    viewLoading.progressBar.visibility = GONE
                }
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


        val events: MutableList<EventDay> = ArrayList()
        val eventsByDay: MutableMap<String, MutableList<String>> = mutableMapOf()
        var sumPayments = 0.0
        var sumSalary = 0.0

        for (item in payments){
            sumPayments += item.realSum ?: 0.0
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

        events.clear()

        for (item in eventsByDay) {
            val colorsList: MutableList<String> = mutableListOf()
            val calendar = Calendar.getInstance()
            for (color in item.value) {
                colorsList.add(color)
            }
            val drawable = CustomDrawable(colorsList)
            val sdf = item.key.split("-")
            calendar.set(sdf[0].toInt(), sdf[1].toInt() - 1, sdf[2].toInt())

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