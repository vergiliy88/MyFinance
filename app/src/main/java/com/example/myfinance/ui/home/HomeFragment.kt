package com.example.myfinance.ui.home

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.databinding.FragmentHomeBinding
import java.util.*
import android.util.Log
import com.applandeo.materialcalendarview.EventDay
import com.example.myfinance.R



class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })


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