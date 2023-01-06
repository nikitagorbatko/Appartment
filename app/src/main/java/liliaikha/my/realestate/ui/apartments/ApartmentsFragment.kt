package liliaikha.my.realestate.ui.apartments

import android.app.Application
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
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
import liliaikha.my.realestate.databinding.FragmentApartmentsBinding
import liliaikha.my.realestate.ui.State

/**
 * A placeholder fragment containing a simple view.
 */
class ApartmentsFragment(
    private val application: Application
) : Fragment() {
    private var _binding: FragmentApartmentsBinding? = null
    private val binding get() = _binding!!
    private var cities = listOf<String>()

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

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        observeChannel()
        onFilterClick()
    }

    private fun observeState() {
        viewModel.viewModelScope.launch {
            viewModel.state.collect {
                with(binding) {
                    when (it) {
                        State.PRESENT -> {
                            recycler.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            (requireActivity() as MainActivity).binding.filterImageView.visibility =
                                View.VISIBLE
                            textError.visibility = View.GONE
                        }
                        State.DOWNLOADING -> {
                            recycler.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                            (requireActivity() as MainActivity).binding.filterImageView.visibility =
                                View.GONE
                            textError.visibility = View.GONE
                        }
                        State.EMPTY -> {
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
            viewModel.channel.collect {
                val adapter = RecyclerViewAdapter(it)
                binding.recycler.adapter = adapter
                binding.recycler.layoutManager = LinearLayoutManager(context)
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.citiesChanel.collect {
                if (it.isNotEmpty()) {
                    cities = it
                }
            }
        }
    }

    private fun onFilterClick() {
        //TODO Костыльно
        (requireActivity() as MainActivity).binding.filterImageView.setOnClickListener {
            var roomSliderFirst = 0.0f
            var roomSliderSecond = 0.0f
            var areaSliderFirst = 0.0f
            var areaSliderSecond = 0.0f

            val dialog = Dialog((requireActivity() as MainActivity))
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog)

            //spinner
            val spinner = dialog.findViewById<Spinner>(R.id.spinner)
            spinner.isEnabled = false
            val citiesAdapter =
                ArrayAdapter<Any?>(
                    (requireActivity() as MainActivity),
                    android.R.layout.simple_spinner_item,
                    cities
                )
            citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = citiesAdapter

            dialog.findViewById<RangeSlider>(R.id.rooms_range_slider)
                .addOnChangeListener { slider, value, fromUser ->
                    val vals = slider.values
                    roomSliderFirst = vals[0]
                    roomSliderSecond = vals[1]
                }
            dialog.findViewById<RangeSlider>(R.id.area_range_slider)
                .addOnChangeListener { slider, value, fromUser ->
                    val vals = slider.values
                    areaSliderFirst = vals[0]
                    areaSliderSecond = vals[1]
                }

            val switch = dialog.findViewById<SwitchCompat>(R.id.switch1)
            switch.setOnCheckedChangeListener { compoundButton, b ->
                spinner.isEnabled = b
            }

            dialog.findViewById<Button>(R.id.button).setOnClickListener {
                val minPrice =
                    dialog.findViewById<TextInputEditText>(R.id.min_cost_edit_text).text?.toString()
                val maxPrice =
                    dialog.findViewById<TextInputEditText>(R.id.max_cost_edit_text).text?.toString()

                val city = cities[spinner.selectedItemPosition]
                viewModel.getFilteredApartments(
                    roomSliderFirst.toInt(),
                    roomSliderSecond.toInt(),
                    areaSliderFirst.toInt(),
                    areaSliderSecond.toInt(),
                    city,
                    minPrice?.toIntOrNull(),
                    maxPrice?.toIntOrNull(),
                    switch.isChecked
                )
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(
            sectionNumber: Int,
            application: Application,
        ): Fragment {
            return ApartmentsFragment(application).apply {
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