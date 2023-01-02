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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import liliaikha.my.realestate.App
import liliaikha.my.realestate.database.ApartmentInfo
import liliaikha.my.realestate.databinding.FragmentMainBinding


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment(private val application: Application) : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var list = listOf<ApartmentInfo>()

    private val viewModel: PageViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = (application as App).database.getDao()
                return PageViewModel(dao) as T
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

        _binding = FragmentMainBinding.inflate(inflater, container, false)


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
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, application: Application): PlaceholderFragment {
            return PlaceholderFragment(application).apply {
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