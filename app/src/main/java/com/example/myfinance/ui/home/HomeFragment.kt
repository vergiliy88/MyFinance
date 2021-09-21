package com.example.myfinance.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.databinding.FragmentHomeBinding
import java.util.*
import com.applandeo.materialcalendarview.EventDay
import com.example.myfinance.R
import com.example.myfinance.ui.base.BaseFragment
import com.example.myfinance.ui.home.add_edit_payment.AddPaymentFragment
import com.example.myfinance.utils.UiUtils


class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

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

        val events: MutableList<EventDay> = ArrayList()
        val calendar = Calendar.getInstance()
        val drawable = CustomDrawable(listOf("#1F85DE", "#DE1F1F", "#DE1F1F", "#1F1FDE", "#1FDE1F"))
        events.add(EventDay(calendar, drawable))
        val calendarView = binding.calendarView
        val buttonAddPayment = binding.buttonAddPayment

        buttonAddPayment.setOnClickListener {
            val fragment = AddPaymentFragment.newInstance()
            UiUtils.replaceFragment(parentFragmentManager, fragment, AddPaymentFragment.TAG_FRAGMENT)
        }

        calendarView.setHeaderColor(R.color.purple_200)
        calendarView.setEvents(events)

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
            it?.let {
                Log.d("QQ", "23")
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}