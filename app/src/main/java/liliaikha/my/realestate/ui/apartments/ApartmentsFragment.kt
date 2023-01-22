package liliaikha.my.realestate.ui.apartments

import android.app.Application
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import liliaikha.my.realestate.App
import liliaikha.my.realestate.MainActivity
import liliaikha.my.realestate.R
import liliaikha.my.realestate.database.ApartmentInfo
import liliaikha.my.realestate.databinding.FragmentApartmentsBinding
import liliaikha.my.realestate.ui.main.MainFragmentViewModel

class ApartmentsFragment(
    private val application: Application,
    private val localFragmentManager: FragmentManager,
    private val mainFragmentViewModel: MainFragmentViewModel
) : Fragment() {
    private lateinit var apartments: ArrayList<ApartmentInfo>
    private lateinit var adapter: RecyclerViewAdapter
    private var _binding: FragmentApartmentsBinding? = null
    private val binding get() = _binding!!
    private var regions = listOf<String>()
    private val sorts = listOf(
        "Количество комнат ↑",
        "Количество комнат ↓",
        "Площадь ↑",
        "Площадь ↓",
        "Стоимость ↑",
        "Стоимость ↓"
    )
    private var maxArea = 0.0f
    private var maxRooms = 1.0f

    private val viewModel: ApartmentsFragmentViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = (application as App).database.getDao()
                return ApartmentsFragmentViewModel(dao) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApartmentsBinding.inflate(inflater, container, false)
        apartments = arrayListOf()
        adapter = RecyclerViewAdapter(apartments, localFragmentManager, mainFragmentViewModel)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)

        observeState()
        observeChannel()
        filterBinding()
    }

    private fun observeState() {
        viewModel.viewModelScope.launch {
            viewModel.state.collect {
                with(binding) {
                    when (it) {
                        ApartmentsFragmentState.PRESENT -> {
                            recycler.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            (requireActivity() as MainActivity).binding.filterImageView.visibility =
                                View.VISIBLE
                            textError.visibility = View.GONE
                        }
                        ApartmentsFragmentState.DOWNLOADING -> {
                            recycler.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                            (requireActivity() as MainActivity).binding.filterImageView.visibility =
                                View.GONE
                            textError.visibility = View.GONE
                        }
                        ApartmentsFragmentState.EMPTY -> {
                            recycler.visibility = View.GONE
                            progressBar.visibility = View.GONE
                            textError.visibility = View.VISIBLE
                            (requireActivity() as MainActivity).binding.filterImageView.visibility =
                                View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun observeChannel() {
        viewModel.viewModelScope.launch {
            viewModel.maxArea.collect {
                maxArea = it
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.maxRooms.collect {
                maxRooms = it
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.apartmentsState.collect {
                if (it.isNotEmpty()) {
                    apartments.clear()
                    apartments.addAll(it)
                    binding.recycler.scrollToPosition(0)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.regionsState.collect {
                if (it.isNotEmpty()) {
                    regions = it
                }
            }
        }
    }

    private fun filterBinding() {
        (requireActivity() as MainActivity).binding.filterImageView.setOnClickListener {
            val dialog = Dialog((requireActivity() as MainActivity))
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog)

            var roomSliderValueFromLocal = viewModel.roomSliderValueFrom
            var roomSliderValueToLocal = viewModel.roomSliderValueTo
            var areaSliderValueFromLocal = viewModel.areaSliderValueFrom
            var areaSliderValueToLocal = viewModel.areaSliderValueTo

            val switchSort = dialog.findViewById<SwitchCompat>(R.id.switch_sort)
            val switchCities = dialog.findViewById<SwitchCompat>(R.id.switch_cities)
            val areaSlider = dialog.findViewById<RangeSlider>(R.id.area_range_slider)
            val roomsSlider = dialog.findViewById<RangeSlider>(R.id.rooms_range_slider)
            val spinnerSort = dialog.findViewById<Spinner>(R.id.spinner_sort)
            val spinnerCities = dialog.findViewById<Spinner>(R.id.spinner_cities)
            val textMinPrice = dialog.findViewById<TextInputEditText>(R.id.min_cost_edit_text)
            val textMaxPrice = dialog.findViewById<TextInputEditText>(R.id.max_cost_edit_text)

            textMinPrice.setText(viewModel.minPrice)
            textMaxPrice.setText(viewModel.maxPrice)
            roomsSlider.setValues(viewModel.roomSliderValueFrom, viewModel.roomSliderValueTo)
            areaSlider.setValues(viewModel.areaSliderValueFrom, viewModel.areaSliderValueTo)
            switchCities.isChecked = viewModel.switchCitiesChecked
            switchSort.isChecked = viewModel.switchSortChecked
            spinnerSort.isEnabled = viewModel.switchSortChecked
            spinnerCities.isEnabled = viewModel.switchCitiesChecked

            val citiesAdapter =
                ArrayAdapter<Any?>(
                    (requireActivity() as MainActivity),
                    android.R.layout.simple_spinner_item,
                    regions
                )
            citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCities.adapter = citiesAdapter
            spinnerCities.setSelection(viewModel.selectedCitySpinnerPosition)

            val sortAdapter =
                ArrayAdapter<Any?>(
                    (requireActivity() as MainActivity),
                    android.R.layout.simple_spinner_item,
                    sorts
                )
            sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSort.adapter = sortAdapter
            spinnerSort.setSelection(viewModel.selectedSortSpinnerPosition)

            roomsSlider.addOnChangeListener { slider, _, _ ->
                with(slider.values) {
                    roomSliderValueFromLocal = this[0]
                    roomSliderValueToLocal = this[1]
                }
            }

            areaSlider.addOnChangeListener { slider, _, _ ->
                with(slider.values) {
                    areaSliderValueFromLocal = this[0]
                    areaSliderValueToLocal = this[1]
                }
            }

            switchCities.setOnCheckedChangeListener { _, b ->
                spinnerCities.isEnabled = b
            }

            switchSort.setOnCheckedChangeListener { _, b ->
                spinnerSort.isEnabled = b
            }

            dialog.findViewById<Button>(R.id.button).setOnClickListener {
                val minPrice = textMinPrice.text?.toString()
                val maxPrice = textMaxPrice.text?.toString()
                val region = regions[spinnerCities.selectedItemPosition]
                val sort = spinnerSort.selectedItemPosition

                viewModel.maxPrice = maxPrice ?: viewModel.maxPrice
                viewModel.minPrice = minPrice ?: viewModel.minPrice
                viewModel.switchSortChecked = switchSort.isChecked
                viewModel.switchCitiesChecked = switchCities.isChecked
                viewModel.roomSliderValueFrom = roomSliderValueFromLocal
                viewModel.roomSliderValueTo = roomSliderValueToLocal
                viewModel.areaSliderValueFrom = areaSliderValueFromLocal
                viewModel.areaSliderValueTo = areaSliderValueToLocal
                viewModel.selectedSortSpinnerPosition = spinnerSort.selectedItemPosition
                viewModel.selectedCitySpinnerPosition = spinnerCities.selectedItemPosition

                viewModel.getFilteredApartments(
                    viewModel.roomSliderValueFrom.toInt(),
                    viewModel.roomSliderValueTo.toInt(),
                    viewModel.areaSliderValueFrom.toInt(),
                    viewModel.areaSliderValueTo.toInt(),
                    region,
                    sort,
                    minPrice?.toIntOrNull(),
                    maxPrice?.toIntOrNull(),
                    switchCities.isChecked,
                )
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            application: Application,
            localFragmentManager: FragmentManager,
            mainFragmentViewModel: MainFragmentViewModel
        ) = ApartmentsFragment(application, localFragmentManager, mainFragmentViewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}