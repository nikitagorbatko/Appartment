package liliaikha.my.realestate.ui

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import liliaikha.my.realestate.R
import liliaikha.my.realestate.databinding.FragmentMainBinding
import liliaikha.my.realestate.ui.apartments.SectionsPagerAdapter
import liliaikha.my.realestate.ui.sort.SortFragment

class MainFragment(private val application: Application): Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sectionsPagerAdapter = SectionsPagerAdapter(application, requireContext(), childFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

        binding.imageView.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container, SortFragment.newInstance(application))
                .addToBackStack("sort")
                .commit()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(application: Application): Fragment {
            return MainFragment(application)
        }
    }
}