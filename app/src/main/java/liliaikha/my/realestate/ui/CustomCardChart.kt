package liliaikha.my.realestate.ui

import android.R
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.cardview.widget.CardView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import liliaikha.my.realestate.databinding.CustomCardChartBinding


class CustomCardChart
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val binding = CustomCardChartBinding.inflate(LayoutInflater.from(context))

    init {
        val data = LineData()
        addView(binding.root)

    }

    fun firstSetChart(list: List<Float>, title: String, cities: List<String>, years: List<String>) {
        binding.textViewChartName.text = title

        val citiesAdapter = ArrayAdapter<Any?>(context, R.layout.simple_spinner_item, cities)
        citiesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        val yearsAdapter = ArrayAdapter<Any?>(context, R.layout.simple_spinner_item, years)
        yearsAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinnerCity.adapter = citiesAdapter
        binding.spinnerYear.adapter = yearsAdapter

        val selectedCity = cities[binding.spinnerCity.selectedItemPosition]
        val selectedYear = cities[binding.spinnerYear.selectedItemPosition]

        list.filter {  }

        setChartSet(list)
    }

    fun updateChart(list: List<Float>) {
        setChartSet(list)
    }

    private fun setChartSet(list: List<Float>) {
        val xAxisValues: ArrayList<String> = arrayListOf(
            "",//Crutch
            "Янв",
            "Фев",
            "Мар",
            "Апр",
            "Май",
            "Июн",
            "Июл",
            "Авг",
            "Сен",
            "Окт",
            "Ноя",
            "Дек"
        )

        val linevalues = ArrayList<Entry>()
        list.forEachIndexed { index, value ->
            linevalues.add(Entry(index + 1f, value))
        }

        val linedataset = LineDataSet(linevalues, "Цена")
        //We add features to our chart
        linedataset.color = resources.getColor(R.color.holo_blue_bright)

        linedataset.circleRadius = 4f
        linedataset.setDrawFilled(true)
        linedataset.valueTextSize = 12F
        linedataset.fillColor = resources.getColor(R.color.holo_green_dark)
        linedataset.mode = LineDataSet.Mode.CUBIC_BEZIER

        val xAxis: XAxis = binding.chart.xAxis

        xAxis.setLabelCount(12, true)
        xAxis.isEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.labelRotationAngle = -45f
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        val data = LineData(linedataset)
        binding.chart.data = data
        binding.chart.setBackgroundColor(resources.getColor(R.color.white))
        binding.chart.animateXY(2000, 2000, Easing.EaseInCubic)
    }
}