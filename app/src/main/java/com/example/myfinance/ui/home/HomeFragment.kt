package com.example.myfinance.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfinance.databinding.FragmentHomeBinding
import java.util.*
import com.applandeo.materialcalendarview.EventDay
import com.example.myfinance.R
import com.example.myfinance.ui.base.BaseFragment


class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
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
        calendarView.setHeaderColor(R.color.purple_200)
        calendarView.setEvents(events)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}