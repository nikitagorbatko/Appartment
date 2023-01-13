package liliaikha.my.realestate.ui.apartments

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import liliaikha.my.realestate.R
import liliaikha.my.realestate.ui.charts.ChartsFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
    private val application: Application,
    private val context: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {
    val fragments = mutableListOf<Fragment>()

    override fun getItem(position: Int): Fragment {
        Log.d("TAG", "${position + 1}")
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return if (position == 0) {
            ApartmentsFragment.newInstance(position + 1, application)
        } else {
            ChartsFragment.newInstance(position + 1, application)
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val createdFragment = super.instantiateItem(container, position) as Fragment
        fragments.add(createdFragment)
        return createdFragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount() = 2
}