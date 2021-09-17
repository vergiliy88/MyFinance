package com.example.myfinance.ui.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfinance.databinding.FragmentStatisticsBinding
import com.example.myfinance.ui.base.BaseFragment

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.utils.ColorTemplate
import android.text.style.ForegroundColorSpan
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.text.style.RelativeSizeSpan
import android.text.SpannableString
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.data.PieDataSet
import android.R
import com.github.mikephil.charting.data.PieEntry


class StatisticsFragment: BaseFragment<FragmentStatisticsBinding>() {

    private lateinit var chart: PieChart

    companion object {
        @JvmStatic
        fun newInstance(): StatisticsFragment {
            return StatisticsFragment()
        }
    }

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