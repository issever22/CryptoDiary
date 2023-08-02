package com.issever.cryptodiary.util

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.issever.cryptodiary.R
import com.issever.cryptodiary.util.extensions.formatDate
import java.util.Date


data class ChartData(val xAxisValues: List<String>, val entries: List<Entry>)

object ChartHelper {

    fun displayHistoricalLineChart(view: View, symbol: String, historyList: List<DoubleArray>,resources: Resources,context : Context) {
        (view as? LineChart)?.let { lineChart ->
            // Chart properties
            lineChart.description.isEnabled = false
            lineChart.setDrawGridBackground(false)

            // Group chart data from historical data
            val chartData = getData(context,historyList)

            //Set the data set for the line
            val lineDataSet = LineDataSet(chartData.entries, resources.getString(R.string.closing_price,symbol))
            lineDataSet.setDrawCircles(false)
            lineDataSet.color = ContextCompat.getColor(lineChart.context, R.color.line_chart_color)
            lineDataSet.valueTextColor =
                ContextCompat.getColor(lineChart.context, R.color.text_color)

            //x-axis properties
            val xAxis = lineChart.xAxis
            xAxis.granularity = 1f
            xAxis.textColor =
                ContextCompat.getColor(lineChart.context, R.color.text_color)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            val formatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return if (value.toInt() < chartData.xAxisValues.size) chartData.xAxisValues[value.toInt()] else ""
                }
            }
            xAxis.valueFormatter = formatter
            xAxis.labelRotationAngle = 90f

            // y-axis properties
            val yAxisRight = lineChart.axisRight
            yAxisRight.isEnabled = false

            val yAxisLeft = lineChart.axisLeft
            yAxisLeft.granularity = 1f
            yAxisLeft.textColor =
                ContextCompat.getColor(lineChart.context, R.color.text_color)

            //Set the data for the line chart
            val data = LineData(lineDataSet)
            lineChart.data = data
            lineChart.animateX(1500)

            //Update the legend for the chart
            val legend = lineChart.legend
            legend.textColor =
                ContextCompat.getColor(lineChart.context, R.color.text_color)
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

            //Draw the chart
            lineChart.invalidate()
        }
    }

    private fun getData(context: Context,historyList: List<DoubleArray>): ChartData {
        val xAxisValues = arrayListOf<String>() // x-axis labels
        val entries = arrayListOf<Entry>() // entries for plotting

        historyList.forEachIndexed { index, entry ->
            val date = Date(entry[0].toLong())
            val label = context.formatDate(date)

            xAxisValues.add(label)
            entries.add(Entry(index.toFloat(), entry[1].toFloat()))
        }

        return ChartData(xAxisValues, entries)
    }
}