package liliaikha.my.realestate.ui.sort

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import liliaikha.my.realestate.App
import liliaikha.my.realestate.databinding.FragmentSortBinding

class SortFragment(private val application: Application) : Fragment() {
    private var _binding: FragmentSortBinding? = null
    private val binding get() = _binding!!
    //города
    //площадь
    //количество комнат
    //цена
    private val viewModel: SortFragmentViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = (application as App).database.getDao()
                return SortFragmentViewModel(dao) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(application: Application): Fragment {
            return SortFragment(application)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}