package liliaikha.my.realestate.ui.main

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
import liliaikha.my.realestate.databinding.ActivityMainBinding
import liliaikha.my.realestate.databinding.FragmentMainBinding
import liliaikha.my.realestate.ui.apartments.SectionsPagerAdapter

class MainFragment private constructor(
    private val activityMainBinding: ActivityMainBinding,
    private val application: Application
) : Fragment() {
    private var sectionsPagerAdapter: SectionsPagerAdapter? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainFragmentViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainFragmentViewModel() as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        //lol
        viewModel.setApartmentsState()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sectionsPagerAdapter == null) {
            sectionsPagerAdapter = SectionsPagerAdapter(
                application,
                application.applicationContext,
                parentFragmentManager,
                viewModel,
                childFragmentManager,
            )
        }

        viewModel.viewModelScope.launch {
            viewModel.state.collect {
                when(it) {
                    MainFragmentState.APARTMENTS -> {
                        activityMainBinding.title.text = "Недвижимость"
                        activityMainBinding.filterImageView.visibility = View.VISIBLE
                        activityMainBinding.tabs.visibility = View.VISIBLE
                    }
                    MainFragmentState.FLAT -> {
                        activityMainBinding.title.text = "Квартира"
                        activityMainBinding.filterImageView.visibility = View.GONE
                        activityMainBinding.tabs.visibility = View.GONE
                    }
                }
            }
        }

        binding.viewPager.adapter = sectionsPagerAdapter
        activityMainBinding.tabs.setupWithViewPager(binding.viewPager)
    }

    companion object {
        @JvmStatic
        fun getInstance(
            activityMainBinding: ActivityMainBinding,
            application: Application
        ) = MainFragment(activityMainBinding, application)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}