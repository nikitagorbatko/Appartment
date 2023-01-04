package liliaikha.my.realestate.ui.apartments

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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import liliaikha.my.realestate.App
import liliaikha.my.realestate.database.ApartmentInfo
import liliaikha.my.realestate.databinding.FragmentApartmentsBinding
import liliaikha.my.realestate.ui.State

/**
 * A placeholder fragment containing a simple view.
 */
class ApartmentsFragment(private val application: Application) : Fragment() {
    private var _binding: FragmentApartmentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ApartmentsFragmentViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = (application as App).database.getDao()
                return ApartmentsFragmentViewModel(dao) as T
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
        _binding = FragmentApartmentsBinding.inflate(inflater, container, false)

        viewModel.viewModelScope.launch {
            viewModel.state.collect {
                with(binding) {
                    when(it) {
                        State.PRESENT -> {
                            recycler.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                        State.DOWNLOADING -> {
                            recycler.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.apartments.collect {
                if (it.isNotEmpty()) {
                    val adapter = RecyclerViewAdapter(it)
                    binding.recycler.adapter = adapter
                    binding.recycler.layoutManager = LinearLayoutManager(context)
                }
            }
        }
        return binding.root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int, application: Application): Fragment {
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