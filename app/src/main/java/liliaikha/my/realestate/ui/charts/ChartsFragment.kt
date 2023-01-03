package liliaikha.my.realestate.ui.charts

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import liliaikha.my.realestate.App
import liliaikha.my.realestate.database.DynamicInfo
import liliaikha.my.realestate.databinding.FragmentChartsBinding

class ChartsFragment(private val application: Application) : Fragment() {
    private var _binding: FragmentChartsBinding? = null
    private val binding get() = _binding!!
    private var list = listOf<DynamicInfo>()
//    private var years: List<String> = listOf()
//    private var cities: List<String> = listOf()

    private val viewModel: ChartsFragmentViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = (application as App).database.getDao()
                return ChartsFragmentViewModel(dao) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel.setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChartsBinding.inflate(inflater, container, false)
            //binding.newChart.setChart()

//        viewModel.viewModelScope.launch {
//            viewModel.cities.collect {
//                cities = it
//            }
//        }
//
//        viewModel.viewModelScope.launch {
//            viewModel.years.collect {
//                years = it.sorted()
//            }
//        }

        viewModel.viewModelScope.launch {
            viewModel.dynamics.collect {
                if (it.isNotEmpty()) {
                    val allCities = mutableListOf<String>()
                    val allYears = mutableListOf<String>()

                    it.forEach { info ->
                        allCities.add(info.city)
                        allYears.add(info.year)
                    }

                    val cities = allCities.distinct()
                    val years = allYears.distinct().sorted()

                    val primaryList = mutableListOf<Float>()
                    val newList = mutableListOf<Float>()
                    val secondaryList = mutableListOf<Float>()
                    val secondaryOfferList = mutableListOf<Float>()
                    it.forEach { info ->
                        primaryList.add(info.primaryPrice.toFloat())
                        newList.add(info.newBuildingOfferCount?.toFloat() ?: 0f)
                        secondaryList.add(info.secondPrice?.toFloat() ?: 0f)
                        secondaryOfferList.add(info.secondOfferCount?.toFloat() ?: 0f)
                    }
                    with(binding) {
                        primaryChart.firstSetChart(primaryList, "Первичный рынок", cities, years)
                        newChart.firstSetChart(newList, "Предложения новостроек", cities, years)
                        secondaryChart.firstSetChart(secondaryList, "Вторичный рынок", cities, years)
                        secondaryOfferChart.firstSetChart(secondaryOfferList, "Вторичная недвижимость", cities, years)
                    }
                }
            }
        }

        return binding.root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int, application: Application): Fragment {
            return ChartsFragment(application).apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}