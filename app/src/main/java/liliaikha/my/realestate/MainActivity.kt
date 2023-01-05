package liliaikha.my.realestate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import liliaikha.my.realestate.databinding.ActivityMainBinding
import liliaikha.my.realestate.ui.apartments.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
        private set
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                application,
                applicationContext,
                binding.imageView,
                supportFragmentManager
            )
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}