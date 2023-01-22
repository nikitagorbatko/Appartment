package liliaikha.my.realestate.ui.apartments

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import liliaikha.my.realestate.R
import liliaikha.my.realestate.databinding.ActivityMainBinding
import liliaikha.my.realestate.ui.charts.ChartsFragment
import liliaikha.my.realestate.ui.main.MainFragmentViewModel

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class SectionsPagerAdapter(
    private val application: Application,
    private val context: Context,
    private val parentFragmentManager: FragmentManager,
    private val mainFragmentViewModel: MainFragmentViewModel,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            ApartmentsFragment.newInstance(application, parentFragmentManager, mainFragmentViewModel)
        } else {
            ChartsFragment.newInstance(application)
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount() = 2
}