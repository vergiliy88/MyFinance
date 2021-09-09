package com.example.myfinance.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.myfinance.databinding.FragmentStatisticsBinding
import com.example.myfinance.ui.base.BaseFragment

class StatisticsFragment(viewModal: StatisticsViewModel): BaseFragment<FragmentStatisticsBinding, String>(viewModal) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textStatistic

        _viewModal.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                textView.text = it
            }
        })
        _viewModal.setData("Test String Statistic")

        return root
    }
}