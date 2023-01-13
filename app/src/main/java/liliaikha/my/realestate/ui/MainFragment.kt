package liliaikha.my.realestate.ui

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import liliaikha.my.realestate.databinding.ActivityMainBinding
import liliaikha.my.realestate.databinding.FragmentMainBinding
import liliaikha.my.realestate.ui.apartments.SectionsPagerAdapter

class MainFragment private constructor(
    private val activityMainBinding: ActivityMainBinding,
    private val application: Application
) : Fragment() {
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(
            activityMainBinding: ActivityMainBinding,
            application: Application
        ) = MainFragment(activityMainBinding, application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        sectionsPagerAdapter = SectionsPagerAdapter(
            application,
            application.applicationContext,
            childFragmentManager
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = sectionsPagerAdapter
        activityMainBinding.tabs.setupWithViewPager(binding.viewPager)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}