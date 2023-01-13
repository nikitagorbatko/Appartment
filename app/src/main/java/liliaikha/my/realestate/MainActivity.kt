package liliaikha.my.realestate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import liliaikha.my.realestate.databinding.ActivityMainBinding
import liliaikha.my.realestate.ui.MainFragment
import liliaikha.my.realestate.ui.apartments.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainFragment = MainFragment.getInstance(binding, application)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, mainFragment)
            .commit()
    }
}