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
import liliaikha.my.realestate.databinding.FragmentChartsBinding


class ChartsFragment(private val application: Application) : Fragment() {
    private var _binding: FragmentChartsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChartsFragmentViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = (application as App).database.getDao()
                return ChartsFragmentViewModel(dao) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

                    with(binding) {
                        primaryChart.firstSetChart(
                            it,
                            "Первичный рынок",
                            cities,
                            years,
                            1
                        )
                        newChart.firstSetChart(
                            it,
                            "Предложения новостроек",
                            cities,
                            years,
                            2
                        )
                        secondaryChart.firstSetChart(
                            it,
                            "Вторичный рынок",
                            cities,
                            years,
                            3
                        )
                        secondaryOfferChart.firstSetChart(
                            it,
                            "Вторичная недвижимость",
                            cities,
                            years,
                            4
                        )
                    }
                }
            }
        }
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