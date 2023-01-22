package liliaikha.my.realestate.ui

import android.R
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.cardview.widget.CardView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import liliaikha.my.realestate.database.DynamicInfo
import liliaikha.my.realestate.databinding.CustomCardChartBinding
import kotlin.math.abs
import kotlin.math.roundToInt


class CustomCardChart
@JvmOverloads constructor(
    private val localContext: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : CardView(localContext, attrs, defStyleAttr) {
    private val binding = CustomCardChartBinding.inflate(LayoutInflater.from(localContext))
    private var selectedCity: String = ""
    private var selectedYear: String = ""
    private lateinit var linedataset: LineDataSet

    init {
        addView(binding.root)
    }

    fun firstSetChart(
        list: List<DynamicInfo>,
        title: String,
        cities: List<String>,
        years: List<String>,
        type: Int,
    ) {
        binding.textViewChartName.text = title
        binding.chart.description.isEnabled = false

        val citiesAdapter = ArrayAdapter<Any?>(localContext, R.layout.simple_spinner_item, cities)
        citiesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        val yearsAdapter = ArrayAdapter<Any?>(localContext, R.layout.simple_spinner_item, years)
        yearsAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinnerCity.adapter = citiesAdapter
        binding.spinnerYear.adapter = yearsAdapter
        binding.spinnerCity.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedCity = cities[p2]
                val result = prepareFloatList(list, type, selectedCity, selectedYear)
                setChartSet(result)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.spinnerYear.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedYear = years[p2]
                val result = prepareFloatList(list, type, selectedCity, selectedYear)
                setChartSet(result)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        selectedCity = cities[binding.spinnerCity.selectedItemPosition]
        selectedYear = years[binding.spinnerYear.selectedItemPosition]

        val resultFloats = prepareFloatList(list, type, selectedCity, selectedYear)

        setChartSet(resultFloats)
    }

    private fun prepareFloatList(
        list: List<DynamicInfo>,
        type: Int,
        selectedCity: String,
        selectedYear: String
    ): MutableList<Float> {
        var resultList = list.filter {
            it.year == selectedYear
        }
        resultList = resultList.filter { it.city == selectedCity }
        val resultFloats = mutableListOf<Float>()
        when (type) {
            1 -> {
                resultList.forEach {
                    resultFloats.add(it.primaryPrice.toFloat())
                }
            }
            2 -> {
                resultList.forEach {
                    resultFloats.add(it.newBuildingOfferCount?.toFloat() ?: 0f)
                }
            }
            3 -> {
                resultList.forEach {
                    resultFloats.add(it.secondPrice?.toFloat() ?: 0f)
                }
            }
            4 -> {
                resultList.forEach {
                    resultFloats.add(it.secondOfferCount?.toFloat() ?: 0f)
                }
            }
        }
        return resultFloats
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

        linedataset = LineDataSet(linevalues, "Цена")
        linedataset.color = resources.getColor(R.color.holo_blue_bright)
        linedataset.circleRadius = 4f
        linedataset.setDrawFilled(true)
        linedataset.valueTextSize = 12F
        linedataset.fillColor = Color.rgb(71, 124, 109)
        linedataset.mode = LineDataSet.Mode.CUBIC_BEZIER

        val xAxis: XAxis = binding.chart.xAxis

        xAxis.isEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.labelRotationAngle = -45f
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        xAxis.setLabelCount(Integer.MAX_VALUE, true)
        xAxis.axisMinimum = 0.5f
        xAxis.axisMaximum = 12.5f
        //xAxis.granularity = 0.5f

        binding.chart.xAxis.valueFormatter = FixedIndexAxisValueFormatter(0f, xAxisValues)
        binding.chart.data = LineData(linedataset)
        binding.chart.setBackgroundColor(resources.getColor(R.color.white))
        binding.chart.setScaleEnabled(false)
        binding.chart.animateXY(2000, 2000, Easing.EaseInCubic)
    }

    private class FixedIndexAxisValueFormatter(
        private val offset: Float,
        private val labels: List<String>
    ) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val x = value - offset
            val index = x.roundToInt()
            return if (index < 0 || index >= labels.size || abs(index - x) > 0.01f) ""
            else labels[index]
        }
    }
}