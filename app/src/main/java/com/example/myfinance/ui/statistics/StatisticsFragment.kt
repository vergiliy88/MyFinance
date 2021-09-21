package com.example.myfinance.ui.statistics

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.databinding.FragmentStatisticsBinding
import com.example.myfinance.domain.utils.Utils
import com.example.myfinance.ui.base.BaseFragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
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

        _viewModal.dateFrom.observe(viewLifecycleOwner, {
            it?.let {dateFrom ->
                buttonDateFrom.text = "${dateFrom.day!!}." +
                                    "${Utils.convertMonthFromCal(dateFrom.month!! + 1)}." +
                                    "${dateFrom.year!!}"
            }
        })

        _viewModal.dateTo.observe(viewLifecycleOwner, {
            it?.let {dateTo->
                buttonDateTo.text = "${dateTo.day!!}." +
                                    "${Utils.convertMonthFromCal(dateTo.month!! + 1)}." +
                                    "${dateTo.year!!}"
            }
        })

        setData()

        return root
    }

     private fun setData() {
        val time = floatArrayOf(55f, 95f, 30f, 15F)
        val activity = arrayOf("Jan", "Feb", "March", "Ert")
        var sum = 0F

        val pieEntires: MutableList<PieEntry> = ArrayList()
        for (i in time.indices) {
            sum += time[i]
            pieEntires.add(PieEntry(time[i], activity[i]))
        }
        val dataSet = PieDataSet(pieEntires, "")
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        chart.description.isEnabled = false
        val data = PieData(dataSet)

        chart.data = data
        chart.invalidate()
        chart.centerText = "Всего - $sum"
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