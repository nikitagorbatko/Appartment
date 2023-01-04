package liliaikha.my.realestate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import liliaikha.my.realestate.databinding.ActivityMainBinding
import liliaikha.my.realestate.ui.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance(application))
            .addToBackStack("main")
            .commit()
    }
}