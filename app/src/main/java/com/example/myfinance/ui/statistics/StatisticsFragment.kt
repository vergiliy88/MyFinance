package com.example.myfinance.ui.statistics

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.R
import com.example.myfinance.databinding.FragmentStatisticsBinding
import com.example.myfinance.domain.models.PaymentJoinPaymentType
import com.example.myfinance.domain.models.PaymentStatistic
import com.example.myfinance.domain.utils.Utils
import com.example.myfinance.ui.base.BaseFragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.collections.ArrayList


class StatisticsFragment: BaseFragment<FragmentStatisticsBinding>() {

    private lateinit var chart: PieChart

    companion object {
        @JvmStatic
        fun newInstance(): StatisticsFragment {
            return StatisticsFragment()
        }
    }

    private lateinit var _viewModal: StatisticsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModal =
            ViewModelProvider(requireActivity()).get(StatisticsViewModel::class.java)
    }

    @ExperimentalCoroutinesApi
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        chart = binding.chart
        val buttonDateFrom = binding.buttonDateFrom
        val buttonDateTo = binding.buttonDateTo
        val buttonApplyFilter = binding.buttonApplyFilter

        buttonDateFrom.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                _viewModal.setDateFrom(year, monthOfYear, dayOfMonth)
                buttonDateFrom.text = "${dayOfMonth}." +
                        "${Utils.convertMonthFromCal(monthOfYear + 1)}." +
                        "$year"

            }, _viewModal.dateFrom.value!!.year!!, _viewModal.dateFrom.value!!.month!!, _viewModal.dateFrom.value!!.day!!)
            dpd.show()
        }

        buttonDateTo.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                _viewModal.setDateTo(year, monthOfYear, dayOfMonth)
                buttonDateTo.text = "${dayOfMonth}." +
                        "${Utils.convertMonthFromCal(monthOfYear + 1)}." +
                        "$year"
            }, _viewModal.dateTo.value!!.year!!, _viewModal.dateTo.value!!.month!!, _viewModal.dateTo.value!!.day!!)
            dpd.show()
        }

        buttonApplyFilter.setOnClickListener {
            _viewModal.applyFilter()
        }

        _viewModal.dateFrom.observe(viewLifecycleOwner, {
            it?.let {date->
                buttonDateFrom.text = "${date.day}." +
                        "${date.month?.plus(1)}." +
                        "${date.year}"
            }
        })

        _viewModal.dateTo.observe(viewLifecycleOwner, {
            it?.let {date->
                buttonDateTo.text = "${date.day}." +
                        "${date.month?.plus(1)}." +
                        "${date.year}"
            }
        })

        _viewModal.payments.observe(viewLifecycleOwner, {
            it?.let{ list ->
                setData(list)
            }
        })

        return root
    }

    private fun setData(list: List<PaymentStatistic>) {

        val colors = mutableListOf<Int>()
        var sum = 0F
        val pieEntires: MutableList<PieEntry> = ArrayList()

        for (item in list) {
            val label = item.paymentTypeName ?: ""
            var valueFloat = 0f
            if (item.realSum != null) {
                valueFloat = item.realSum?.toFloat() ?: 0f
            }
            colors.add(Color.parseColor(item.paymentColor))
            sum += valueFloat
            pieEntires.add(PieEntry(valueFloat, label))
        }

        val dataSet = PieDataSet(pieEntires, "")
        dataSet.colors = colors
        chart.description.isEnabled = false
        val data = PieData(dataSet)

        chart.data = data
        chart.invalidate()
        chart.centerText = "Всего - $sum${resources.getString(R.string.currency)}"
        chart.setDrawEntryLabels(false)
        chart.contentDescription = ""
        chart.setEntryLabelTextSize(12F)
        chart.holeRadius = 75F

        val legend: Legend = chart.legend
        legend.form = Legend.LegendForm.CIRCLE
        legend.textSize = 12f
        legend.formSize = 20f
        legend.formToTextSpace = 2f
    }
}